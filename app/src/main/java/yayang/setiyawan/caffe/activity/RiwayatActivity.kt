package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_riwayat.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.detail.DetailTransaksiActivity
import yayang.setiyawan.caffe.adapter.AdapterHistory
import yayang.setiyawan.caffe.app.ApiServices
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.unit.UnitApiConfig

class RiwayatActivity : AppCompatActivity() {
    lateinit var s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)
        Helper().setToolbar(this, toolbar, "History")
        s = SharedPref(this)
    }
//    fun getRiwayat(){
//        val id =s.getString(s.id.toString())
//        ApiServices.getRiwayat(id).enqueue(object : Callback<ResponModel>{
//            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                val res = response.body()
//                if (res?.success == 1){
//                    displayRiwayat(res.transaksis)
//                }
//            }
//            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//            }
//
//        })
//    }
//
//    fun displayRiwayat(transaksis: ArrayList<Transaksi>) {
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rv_riwayat.adapter = AdapterHistory(transaksis, object : AdapterHistory.Listeners{
//            override fun onClicked(data: Transaksi) {
//                val json = Gson().toJson(data,Transaksi::class.java)
//                val intent = Intent(this@RiwayatActivity, DetailTransaksiActivity::class.java)
//                intent.putExtra("transaksi",json)
//                startActivity(intent)
//            }
//        })
//        rv_riwayat.layoutManager = layoutManager
//    }
    override fun onResume() {
//        getRiwayat()
//        displayRiwayat(transaksis )
        super.onResume()
    }
}