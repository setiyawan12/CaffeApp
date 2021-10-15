package yayang.setiyawan.caffe.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.ProductCoffeContract
import yayang.setiyawan.caffe.model.ListResponse
import yayang.setiyawan.caffe.model.Produk

class ProductCoffePresenter(v:ProductCoffeContract.View):ProductCoffeContract.Presenter {
    private var view:ProductCoffeContract.View?=v
    override fun getProdukCoffe() {
        val webservices = ApiConfig.APIService()
        val request = webservices.getProdukCoffe()
        request.enqueue(object : Callback<ListResponse<Produk>>{
            override fun onResponse(
                call: Call<ListResponse<Produk>>,
                response: Response<ListResponse<Produk>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        if (body.data.isNotEmpty()){
                            view?.attachToRecycler(body.data)
                            view?.emptydata(false)
                        }else{
                            view?.emptydata(true)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse<Produk>>, t: Throwable) {
                view?.toast("terjadi kesalahan server")
            }

        })
    }

    override fun destroy() {
        view = null
    }
}