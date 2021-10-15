package yayang.setiyawan.caffe.app

import retrofit2.Call
import retrofit2.http.GET
import yayang.setiyawan.caffe.model.ListResponse
import yayang.setiyawan.caffe.model.Produk

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
}