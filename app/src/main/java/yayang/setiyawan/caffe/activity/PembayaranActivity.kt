package yayang.setiyawan.caffe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pembayaran.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.room.MyDatabase
import yayang.setiyawan.caffe.unit.UnitApiConfig

class PembayaranActivity : AppCompatActivity() {
    lateinit var myDb : MyDatabase
    lateinit var s:SharedPref
    var totalHarga = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        s = SharedPref(this)
        myDb = MyDatabase.getInstance(this)!!
        mainButton()
    }

    private fun mainButton(){
        btn_bayar.setOnClickListener {
            bayar()
        }
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
                produk.id = "" + p.id
                produk.total_item = ""+p.jumlah
                produk.total_harga = ""+(p.jumlah * Integer.valueOf(p.harga))
                produk.catatan = "catatan baru"
                produks.add(produk)
            }
        }
        val checkout = Chekout()
        checkout.customer_id = ""+s.getString(s.id.toString())
        checkout.total_item = ""+totalItem
        checkout.total_harga = ""+totalHarga
        checkout.name = ""+s.getString(s.name)
        checkout.meja = "tes"
        checkout.produks = produks

        UnitApiConfig.instanceRetrofit.chekout(checkout).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                if (respon.success == 1){
                    Toast.makeText(this@PembayaranActivity,"Pembayaran Berhasil",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@PembayaranActivity,"Gagal",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@PembayaranActivity,"Terjadi kesalahan server",Toast.LENGTH_SHORT).show()
            }

        })
    }
}