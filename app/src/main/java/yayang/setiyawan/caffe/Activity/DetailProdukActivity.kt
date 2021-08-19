package yayang.setiyawan.caffe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_produk.*
import yayang.setiyawan.caffe.Helper.Helper
import yayang.setiyawan.caffe.Model.Produk
import yayang.setiyawan.caffe.R


class DetailProdukActivity : AppCompatActivity() {
    lateinit var produk: Produk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        getInfo()
    }
    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data,Produk::class.java)
        tv_detailnama.text = produk.name
        tv_harganya.text= Helper().gantiRupiah(produk.harga)
        tv_detaildeskripsi.text = produk.deskripsi
        val img = produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400,400)
            .into(image)
    }
}