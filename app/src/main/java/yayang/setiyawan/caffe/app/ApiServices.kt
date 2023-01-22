package yayang.setiyawan.caffe.app

import retrofit2.Call
import retrofit2.http.*
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

    @POST("api/transaksi")
    fun chekout(
        @Body data: Chekout
    ): Call<ResponModel>
    @POST("api/payment")
    fun checkoutcashless(
        @Body data: Chekout
    ): Call<ResponModel>

    @GET("api/transaksi/customer/{id}")
    fun getRiwayat(
        @Path("id") id: String
    ):Call<ResponModel>
    @GET("api/transaksi/customer/midtrans/{id}")
    fun getRiwayatMidtrans(
        @Path("id") id: String
    ):Call<ResponModel>
    @GET("api/transaksi/customer/midtransdetail/{id}")
    fun getRiwayatTransaction(
        @Path("id") id: String
    ):Call<WrappedResponse<Midtrans>>
    @GET("meja/{id}")
    fun getMeja(
        @Path("id") id: String
    ):Call<ResponModel>
}