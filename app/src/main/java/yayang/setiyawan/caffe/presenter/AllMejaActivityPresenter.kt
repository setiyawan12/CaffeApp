package yayang.setiyawan.caffe.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.contract.GetMejaActivityContract
import yayang.setiyawan.caffe.model.ListResponse
import yayang.setiyawan.caffe.model.Meja
import yayang.setiyawan.caffe.unit.UnitApiConfig

class AllMejaActivityPresenter (v:GetMejaActivityContract.AllMejaActivityView?):GetMejaActivityContract.AllMejaPresenter{
    private var view : GetMejaActivityContract.AllMejaActivityView?=v
    override fun allmeja() {
        UnitApiConfig.instanceRetrofit.Allmeja().enqueue(object :Callback<ListResponse<Meja>>{
            override fun onResponse(
                call: Call<ListResponse<Meja>>,
                response: Response<ListResponse<Meja>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.attachToRecycler(body.data)
                    }
                }
            }
            override fun onFailure(call: Call<ListResponse<Meja>>, t: Throwable) {
                view?.showToast("terjadi kesalahan server")
            }
        })
    }

    override fun destroy() {
        view = null
    }

}