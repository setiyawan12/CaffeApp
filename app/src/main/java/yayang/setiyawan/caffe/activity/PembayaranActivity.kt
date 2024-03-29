package yayang.setiyawan.caffe.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import kotlinx.android.synthetic.main.activity_pembayaran.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.app.Constant
import yayang.setiyawan.caffe.contract.PembayaranActivityContract
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.presenter.PembayaranActivityPresenter
import yayang.setiyawan.caffe.room.MyDatabase
import yayang.setiyawan.caffe.unit.Config

class PembayaranActivity : AppCompatActivity(), TransactionFinishedCallback, PembayaranActivityContract.View {
    lateinit var myDb : MyDatabase
    lateinit var s:SharedPref
    val checkout = Chekout()
    private lateinit var presenter : PembayaranActivityContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        s = SharedPref(this)
        myDb = MyDatabase.getInstance(this)!!
        mainButton()
        setUpMidtrans()
        cekMeja()
        presenter = PembayaranActivityPresenter(this)
    }
    private fun setUpMidtrans(){
        SdkUIFlowBuilder.init()
            .setClientKey(Config.baseClientKey)
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setMerchantBaseUrl(Config.baseUrlMidtrans)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()
    }
    private fun mainButton(){
        btn_bayar.setOnClickListener {
            progres()
            bayar()
        }
        btn_payment.setOnClickListener {
            payment()
        }
    }
    fun cekMeja(){
        val meja = Constant.getIdMeja(this)
        toast(meja.toString())
    }

    private fun payment(){
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val order_id = "TRX-${System.currentTimeMillis()}"
        val produks = ArrayList<Chekout.Item>()
        for (p in listProduk){
            if (p.selected){
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))
                val produk = Chekout.Item()
                produk.id = p.id.toString()
                produk.total_item = p.jumlah.toString()
                produk.total_harga = "${(p.jumlah * Integer.valueOf(p.harga))}"
                produk.catatan = "catatan baru"
                produks.add(produk)
            }
        }
        checkout.customer_id = s.getString(s.id.toString())
        checkout.total_item = totalItem.toString()
        checkout.total_harga = totalHarga.toString()
        checkout.name =s.getString(s.name)
        checkout.meja =Constant.getIdMeja(this).toString()
        checkout.order_id = order_id
        checkout.produks = produks
        val transactionRequest = TransactionRequest(order_id,totalHarga!!.toDouble())
        val detail = ItemDetails(System.currentTimeMillis().toString(),totalHarga.toDouble(),1.toInt(),"JATINAN")
        val itemDetails = ArrayList<ItemDetails>()
        itemDetails.add(detail)
        uiKitDetails(transactionRequest)
        transactionRequest.itemDetails = itemDetails
        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)
    }
    private fun progres(){
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Oder Prosses"
        pDialog.setCancelable(false)
        pDialog.show()
    }
    private fun bayar(){
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<Chekout.Item>()
        for (p in listProduk){
            if (p.selected){
                totalItem += p.jumlah
                totalHarga += (p.jumlah * Integer.valueOf(p.harga))
                val produk = Chekout.Item()
                produk.id = p.id.toString()
                produk.total_item = p.jumlah.toString()
                produk.total_harga = "${(p.jumlah * Integer.valueOf(p.harga))}"
                produk.catatan = "catatan baru"
                produks.add(produk)
            }
        }
        val idMeja = Constant.getIdMeja(this)
        checkout.customer_id = s.getString(s.id.toString())
        checkout.total_item = totalItem.toString()
        checkout.total_harga = totalHarga.toString()
        checkout.name =s.getString(s.name)
        checkout.meja =idMeja.toString()
        checkout.produks = produks
        presenter.bayarCash(this, checkout)
    }
    private fun uiKitDetails(transactionRequest: TransactionRequest){
        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier="yayang setiyawan"
        customerDetails.firstName="yayang"
        customerDetails.lastName="setiyawan"
        customerDetails.email ="Noreply@gmail.com"
        customerDetails.phone="09876543222"
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
    override fun onTransactionFinished(p0: TransactionResult?) {
        when {
            p0?.response != null -> {
                when(p0.status){
                    TransactionResult.STATUS_SUCCESS -> {
                        Log.e("ENOG", "DONE : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Successfully")
                    }
                    TransactionResult.STATUS_PENDING -> {
                        Log.e("ENOG", "PENDING : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Pending")
                        presenter.bayarCashless(this,checkout)
                        val myDb = MyDatabase.getInstance(this)
                        for (produk in checkout.produks){
                            myDb?.daoKeranjang()?.deleteById(produk.id)
                        }
                        startActivity(Intent(this,MainActivity::class.java).also {
                            finish()
                        })
                    }
                    TransactionResult.STATUS_FAILED -> {
                        Log.e("ENOG", "FAILED : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Failed")
                    }
                    TransactionResult.STATUS_INVALID -> {
                        Log.e("ENOG", "INVALID : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Invalid")
                    }
                }
            }
            p0?.isTransactionCanceled == true -> {
                toast("Transaction Cancelled")
            }
            else -> {
                toast("Transaction Failed")
            }
        }
        finish()
    }
    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
    override fun successBayarCash(pindah: Intent) {
        startActivity(pindah)
    }
}