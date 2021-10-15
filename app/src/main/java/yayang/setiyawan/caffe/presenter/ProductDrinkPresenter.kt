package yayang.setiyawan.caffe.presenter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.ProductDrinkContract
import yayang.setiyawan.caffe.model.ListResponse
import yayang.setiyawan.caffe.model.Produk

class ProductDrinkPresenter(v: ProductDrinkContract.View):ProductDrinkContract.Presenter {
    private var view:ProductDrinkContract.View?=v
    override fun getProdukDrink() {
        val webservices = ApiConfig.APIService()
        val request = webservices.getProdukDrink()
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
                view?.toast("tejadi kesalahan server")
            }

        })
    }

    override fun destroy() {
        view = null
    }
}