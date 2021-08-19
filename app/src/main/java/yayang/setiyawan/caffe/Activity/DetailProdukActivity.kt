package yayang.setiyawan.caffe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_produk.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import yayang.setiyawan.caffe.Helper.Helper
import yayang.setiyawan.caffe.Model.Produk
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.Room.MyDatabase



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
}