package yayang.setiyawan.caffe.presenter

import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_cash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.HistoryCashFragmentContract
import yayang.setiyawan.caffe.model.ResponModel

class CashFragmentPresenter(v:HistoryCashFragmentContract.view):HistoryCashFragmentContract.presenter {
    private var view:HistoryCashFragmentContract.view? =v
    override fun getcash(id: String) {
        val webservices = ApiConfig.APIService()
        val request = webservices.getRiwayat(id)

        request.enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()
                if (res?.success == 200){
                    view?.attachtoRecyle(res.transaksis)
                }else if(res?.success == 0){
                    view?.emptyData()
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                view?.toast("Terjadi Kesalahan Server")
            }

        })
    }
}