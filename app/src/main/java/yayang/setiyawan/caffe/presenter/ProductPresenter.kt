package yayang.setiyawan.caffe.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.contract.ProductContract
import yayang.setiyawan.caffe.model.ListResponse
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.app.ApiConfig

class ProductPresenter(v:ProductContract.View?):ProductContract.Presenter {
    private var view:ProductContract.View?=v
    override fun getAllProduct() {
        val webservices = ApiConfig.APIService()
        val request = webservices.getProduk()
        request.enqueue(object : Callback<ListResponse<Produk>>{
            override fun onResponse(
                call: Call<ListResponse<Produk>>,
                response: Response<ListResponse<Produk>>
            ) {
                if (response.isSuccessful){
                    val  body = response.body()
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

    override fun getProductFood() {
        val webServices = ApiConfig.APIService()
        val request = webServices.getProdukFood()
        request.enqueue(object : Callback<ListResponse<Produk>>{
            override fun onResponse(
                call: Call<ListResponse<Produk>>,
                response: Response<ListResponse<Produk>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        if (body.data.isNotEmpty()){
                            view?.attachToRecyclerFood(body.data)
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
    override fun getProductSnack() {
        val webServices = ApiConfig.APIService()
        val request = webServices.getProdukSnack()
        request.enqueue(object : Callback<ListResponse<Produk>>{
            override fun onResponse(
                call: Call<ListResponse<Produk>>,
                response: Response<ListResponse<Produk>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        if (body.data.isNotEmpty()){
                            view?.attachToRecyclerSnack(body.data)
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