package yayang.setiyawan.caffe.app

import retrofit2.Call
import retrofit2.http.GET
import yayang.setiyawan.caffe.Model.ResponModel

interface ApiServices {
    @GET("produk")
    fun getProduk(): Call<ResponModel>
}