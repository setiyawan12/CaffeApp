package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import yayang.setiyawan.caffe.fragment.KeranjangFragment
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.room.MyDatabase



class DetailProdukActivity : AppCompatActivity() {
    lateinit var produk: Produk
    lateinit var myDb:MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        myDb = MyDatabase.getInstance(this)!!
        getInfo()
        checkKeranjang()
        mainButton()
        SdkUIFlowBuilder.init()
            .setClientKey("SB-Mid-client-PBi0krZkXxiUQLH7")
            .setContext(this)
            .setTransactionFinishedCallback {
                println("TRANSAKSI RESPONSE"+it)
            }
            .setMerchantBaseUrl("https://cafe-server-setiyawan.herokuapp.com/api/")
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()

        payment.setOnClickListener {
            val data = intent.getStringExtra("extra")
            produk = Gson().fromJson<Produk>(data,Produk::class.java)
            val namaPoduk = produk.name
            val deskripsi = produk.deskripsi
            val harga = produk.harga
            val transactionRequest = TransactionRequest("setiyawan"+System.currentTimeMillis().toShort()+"",harga!!.toDouble())
            val detail = com.midtrans.sdk.corekit.models.ItemDetails("caffe",harga.toDouble(),1.toInt(),namaPoduk)
            val itemDetails = ArrayList<com.midtrans.sdk.corekit.models.ItemDetails>()
            itemDetails.add(detail)
            uiKitDetails(transactionRequest)
            transactionRequest.itemDetails = itemDetails
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)
        }
    }

    fun mainButton(){
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjang().getProduk(produk.id)
            if (data == null){
                insert()
            }else{
                data.jumlah += 1
                update(data)
            }
        }
        btn_toKeranjang.setOnClickListener {
            val intent = Intent(this,KeranjangFragment::class.java)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data,Produk::class.java)
        tv_detailnama.text = produk.name
        tv_harganya.text= Helper().gantiRupiah(produk.harga!!)
        tv_detaildeskripsi.text = produk.deskripsi
        val img = produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(image)
        Helper().setToolbar(this,toolbar,produk.name.toString())
    }
    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Keranjang")
                    .setContentText("Di Tambah Ke Keranjang")
                    .show()
            }
        )
    }
    private fun update(data:Produk){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
//                    Toast.makeText(this, "Berhasil menambah kekeranjang", Toast.LENGTH_SHORT).show(
            })
    }
    private fun checkKeranjang() {
        val dataKranjang = myDb.daoKeranjang().getAll()
        if (dataKranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKranjang.size.toString()
        } else {
            div_angka.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
    private fun uiKitDetails(transactionRequest: TransactionRequest){
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier="yayang setiyawan"
        customerDetails.firstName="yayang"
        customerDetails.lastName="setiyawan"
        customerDetails.phone="09876543222"
        customerDetails.email="test@gmail.com"
        val shippingAddress = ShippingAddress()
        shippingAddress.firstName="yayang"
        shippingAddress.lastName="setiyawan"
        shippingAddress.phone="098765432"
        shippingAddress.address="mindaka"
        shippingAddress.city="Tegal"
        shippingAddress.postalCode="12345"
        shippingAddress.countryCode="IDN"
        customerDetails.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.firstName="yayang"
        billingAddress.lastName="setiyawan"
        billingAddress.phone="567890773733"
        billingAddress.address="Mindaka"
        billingAddress.city="tegal"
        billingAddress.postalCode="123456"
        billingAddress.countryCode="IDN"

        customerDetails.billingAddress = billingAddress
        transactionRequest.customerDetails = customerDetails
    }
}