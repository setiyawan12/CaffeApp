package yayang.setiyawan.caffe.presenter

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.Constant
import yayang.setiyawan.caffe.contract.GetMejaActivityContract
import yayang.setiyawan.caffe.model.Meja
import yayang.setiyawan.caffe.model.WrappedResponseMeja
import yayang.setiyawan.caffe.unit.UnitApiConfig


class MejaActivityPresenter(v:GetMejaActivityContract.GetMejaActivityView?):GetMejaActivityContract.GetMejaPresenter {
    private var view : GetMejaActivityContract.GetMejaActivityView?=v
    override fun meja(no_meja: String, context: Context) {
        UnitApiConfig.instanceRetrofit.getMejas(no_meja).enqueue(object :Callback<WrappedResponseMeja<Meja>>{
            override fun onResponse(
                call: Call<WrappedResponseMeja<Meja>>,
                response: Response<WrappedResponseMeja<Meja>>
            ) {
                if (response.isSuccessful){
                    val res = response.body()
                    if (res?.success == 1){
                        if (res?.data.status == "Tidak Aktif"){
                            view?.successMeja()
                            Constant.setIdMeja(context,res.data.id!!)
                        }else{
                            view?.showLoading()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<WrappedResponseMeja<Meja>>, t: Throwable) {
                view?.showToast("terjadi kesalahan")
            }

        })
    }

    override fun destroy() {

    }
//    override fun meja(no_meja: String, context: Context) {
//        UnitApiConfig.instanceRetrofit.getMeja(no_meja).enqueue(object :Callback<ResponModel>{
//            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
//                val res = response.body()
//                if (res?.success == 1){
//                    if (res?.message == "Tidak Aktif"){
////                        view?.showToast("meja tidak aktif")
//                        view?.showToast(res.data.id.toString())
//                    }else{
//                        view?.showToast("meja aktif")
//                    }
//                }
//            }
//            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
//                view?.showToast("terjadi kesalahan")
//            }
//        })
//    }
//    override fun destroy() {
//    }

}