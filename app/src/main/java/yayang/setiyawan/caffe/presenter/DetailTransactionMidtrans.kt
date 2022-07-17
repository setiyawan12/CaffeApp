package yayang.setiyawan.caffe.presenter

import android.content.Context
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.DetailHistoryTransactionMidtrans
import yayang.setiyawan.caffe.model.Midtrans
import yayang.setiyawan.caffe.model.WrappedResponse

class DetailTransactionMidtrans(v:DetailHistoryTransactionMidtrans.view):DetailHistoryTransactionMidtrans.presenter {
    private var view:DetailHistoryTransactionMidtrans.view= v
    override fun detailMidtrans(id: String,context: Context) {
        val webServices = ApiConfig.APIService()
        val  request = webServices.getRiwayatTransaction(id)
        request.enqueue(object : Callback<WrappedResponse<Midtrans>>{
            override fun onResponse(
                call: Call<WrappedResponse<Midtrans>>,
                response: Response<WrappedResponse<Midtrans>>
            ) {
                val body =  response.body()?.data
                if (body != null) {
                    view?.success(body)
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Midtrans>>, t: Throwable) {
                view?.toast("Tejadi  Kesalahan server")
            }

        })
    }

}