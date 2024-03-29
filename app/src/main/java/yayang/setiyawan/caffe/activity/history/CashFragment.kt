package yayang.setiyawan.caffe.activity.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_cash.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.detail.DetailTransaksiActivity
import yayang.setiyawan.caffe.adapter.AdapterHistory
import yayang.setiyawan.caffe.contract.HistoryCashFragmentContract
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.presenter.CashFragmentPresenter

class CashFragment : Fragment(),HistoryCashFragmentContract.view {
    lateinit var presenter:HistoryCashFragmentContract.presenter
    lateinit var s:SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_cash, container, false)
        init(view)
        s = SharedPref(requireActivity())
        presenter = CashFragmentPresenter(this)
        return view
    }
    fun getRiwayat(){
        val id = s.getString(s.id.toString())
        presenter.getcash(id)
//        UnitApiConfig.instanceRetrofit.getRiwayat(id).enqueue(object : Callback<ResponModel>{
//            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                val res = response.body()
//                if (res?.success == 200){
//                    displayRiwayat(res.transaksis)
//                }else if(res?.success == 0){
//                    tv_empty.visibility = View.VISIBLE
//                }
//            }
//            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                Helper().alertFailed(requireActivity(),"Server Failed")
//            }
//        })
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

    override fun onStart() {
        super.onStart()
        getRiwayat()
    }

    override fun attachtoRecyle(data: ArrayList<Transaksi>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_cash?.adapter = AdapterHistory(data, object :AdapterHistory.Listeners{
            override fun onClicked(data: Transaksi) {
                val json = Gson().toJson(data,Transaksi::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                intent.putExtra("transaksi",json)
                startActivity(intent)
            }
        })
        rv_cash?.layoutManager = layoutManager
    }

    override fun emptyData() {
        tv_empty.visibility = View.VISIBLE
    }

    override fun toast(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

}