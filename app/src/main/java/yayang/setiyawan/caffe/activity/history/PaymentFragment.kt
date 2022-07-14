package yayang.setiyawan.caffe.activity.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_cash.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.tv_empty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.detail.DetailTransaksiMidtransActivity
import yayang.setiyawan.caffe.adapter.AdapterHistory
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.unit.UnitApiConfig

class PaymentFragment : Fragment() {
    lateinit var s:SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_payment, container, false)
        init(view)
        s = SharedPref(requireActivity())
        getRiwayat()
        return view
    }

    fun getRiwayat(){
        val id = s.getString(s.id.toString())
        UnitApiConfig.instanceRetrofit.getRiwayatMidtrans(id).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()
                if (res?.success == 200){
                    displayRiwayat(res.transaksis)
                }else if(res?.success == 0){
                    tv_empty.visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Helper().alertFailed(requireActivity(),"server error")
            }

        })
    }

    fun displayRiwayat(transaksis:ArrayList<Transaksi>){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_payment?.adapter = AdapterHistory(transaksis,object : AdapterHistory.Listeners{
            override fun onClicked(data: Transaksi) {
                val json = Gson().toJson(data,Transaksi::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiMidtransActivity::class.java)
                intent.putExtra("transaksi",json)
                startActivity(intent)
            }
        })
        rv_payment?.layoutManager = layoutManager
    }

    private fun init(view: View){

    }

    override fun onResume() {
        super.onResume()
        getRiwayat()
    }
}