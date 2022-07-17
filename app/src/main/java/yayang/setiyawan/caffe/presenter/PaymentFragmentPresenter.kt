package yayang.setiyawan.caffe.presenter


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.HistoryPaymentFragmentContract
import yayang.setiyawan.caffe.model.ResponModel

class PaymentFragmentPresenter(v:HistoryPaymentFragmentContract.view):HistoryPaymentFragmentContract.presenter {
    private var view:HistoryPaymentFragmentContract.view? =v
    override fun getPayment(id: String) {
        val webservices = ApiConfig.APIService()
        val request = webservices.getRiwayatMidtrans(id)

        request.enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()
                if (res?.success == 200){
                    view?.attachtoRecyle(res.transaksis)
                    println(" Res Transaksi ${res.transaksis[0]}")
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