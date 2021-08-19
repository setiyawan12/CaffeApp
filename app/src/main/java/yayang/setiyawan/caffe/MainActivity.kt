package yayang.setiyawan.caffe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.Adapter.AdapterProduk
import yayang.setiyawan.caffe.Helper.SharedPref
import yayang.setiyawan.caffe.Model.Category
import yayang.setiyawan.caffe.Model.Produk
import yayang.setiyawan.caffe.Model.ResponModel
import yayang.setiyawan.caffe.app.ApiConfig

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = SharedPref(this)
        getProduk()
    }
    private var listProduk:ArrayList<Produk> = ArrayList()
    private fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if(res.success == 1) {
                    val arrayProduk = ArrayList<Produk>()
                    for (p in res.produks) {
                        arrayProduk.add(p)
                    }
                    listProduk = arrayProduk
                    showProduk()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun showProduk(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_produk.adapter = AdapterProduk(this,listProduk)
        rv_produk.layoutManager = layoutManager
    }
}