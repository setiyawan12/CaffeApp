package yayang.setiyawan.caffe.activity.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import yayang.setiyawan.caffe.contract.HistoryPaymentFragmentContract
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.presenter.PaymentFragmentPresenter
import yayang.setiyawan.caffe.unit.UnitApiConfig
import kotlin.math.log

class PaymentFragment : Fragment(),HistoryPaymentFragmentContract.view {
    private lateinit var presenter:HistoryPaymentFragmentContract.presenter
    lateinit var s:SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_payment, container, false)
        s = SharedPref(requireActivity())
        presenter = PaymentFragmentPresenter(this)
        getRiwayat()
        return view
    }
    fun getRiwayat(){
        val id = s.getString(s.id.toString())
        presenter.getPayment(id)
    }
    override fun attachtoRecyle(data: ArrayList<Transaksi>) {
        Log.d("Transaksis",data.toString())

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_payment?.adapter = AdapterHistory(data,object : AdapterHistory.Listeners{
            override fun onClicked(data: Transaksi) {
                val json = Gson().toJson(data,Transaksi::class.java)
                val intent = Intent(requireActivity(), DetailTransaksiMidtransActivity::class.java)
                intent.putExtra("transaksi",json)
                startActivity(intent)
            }
        })
        rv_payment?.layoutManager = layoutManager
    }
    override fun emptyData() {
      tv_empty.visibility = View.VISIBLE
    }

    override fun toast(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getRiwayat()
    }
}