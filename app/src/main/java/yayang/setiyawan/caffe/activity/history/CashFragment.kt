package yayang.setiyawan.caffe.activity.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_cash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.detail.DetailTransaksiActivity
import yayang.setiyawan.caffe.adapter.AdapterHistory
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.unit.UnitApiConfig

class CashFragment : Fragment() {
    lateinit var s:SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_cash, container, false)
        init(view)
        s = SharedPref(requireActivity())
        return view
    }
    fun getRiwayat(){
        val id = s.getString(s.id.toString())
        UnitApiConfig.instanceRetrofit.getRiwayat(id).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()
                if (res?.success == 1){
                    displayRiwayat(res.transaksis)
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Helper().alertFailed(requireActivity(),"Server Failed")
            }
        })
    }
    fun displayRiwayat(transaksis:ArrayList<Transaksi>){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_cash?.adapter = AdapterHistory(transaksis, object :AdapterHistory.Listeners{
            override fun onClicked(data: Transaksi) {
                val json = Gson().toJson(data,Transaksi::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                intent.putExtra("transaksi",json)
                startActivity(intent)
            }
        })
        rv_cash?.layoutManager = layoutManager
    }

    private fun init(view: View){
        val rv_cash = view.findViewById<RecyclerView>(R.id.rv_cash)
    }

    override fun onResume() {
        super.onResume()
        getRiwayat()
    }

}