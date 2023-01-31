package yayang.setiyawan.caffe.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.contract.CancleTransactionContract
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.unit.UnitApiConfig

class CancleTransactionPresenter(v:CancleTransactionContract.CancleTransactionContractView?):CancleTransactionContract.CancleTransactionContractPresenter {
    private var view :CancleTransactionContract.CancleTransactionContractView?=v
    override fun cancle(id: String) {
        view?.showLoading()
        UnitApiConfig.instanceRetrofit.cancleTransaction(id).enqueue(object :Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful){
                    val res = response.body()
                    if (res?.success == 1){
                        view?.showToast("Berhasil Cancle Pesanan")
                        view?.hideLoading()
                    }
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                view?.showToast("Terjadi Kesalahan Server")
            }
        })
    }

    override fun destroy() {
        view = null
    }

}