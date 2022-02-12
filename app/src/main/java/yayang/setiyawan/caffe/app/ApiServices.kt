package yayang.setiyawan.caffe.app

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import yayang.setiyawan.caffe.model.*

interface ApiServices {
    @GET("api/produk")
    fun getProduk(): Call<ListResponse<Produk>>
    @GET("api/produk/1")
    fun getProdukFood():Call<ListResponse<Produk>>
    @GET("api/produk/3")
    fun getProdukSnack():Call<ListResponse<Produk>>
    @GET("api/produk/2")
    fun getProdukDrink():Call<ListResponse<Produk>>
    @GET("api/produk/4")
    fun getProdukCoffe():Call<ListResponse<Produk>>
//    @FormUrlEncoded
//    @POST("api/login")
//    fun login(
//        @Field("email") email: String,
//        @Field("password")password: String,
//    ):Call<WrappedResponse<User>>
}