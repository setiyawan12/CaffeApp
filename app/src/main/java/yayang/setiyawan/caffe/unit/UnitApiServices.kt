package yayang.setiyawan.caffe.unit

import retrofit2.Call
import retrofit2.http.*
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.model.Midtrans
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.WrappedResponse

interface UnitApiServices {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password")password: String,
    ): Call<ResponModel>

    @FormUrlEncoded
    @POST("customer")
    fun customer(
        @Field("name") name:String
    ):Call<ResponModel>

    @POST("transaksi")
    fun chekout(
        @Body data: Chekout
    ): Call<ResponModel>
    @POST("payment")
    fun payment(
        @Body data: Chekout
    ): Call<ResponModel>

    @GET("transaksi/customer/{id}")
    fun getRiwayat(
        @Path("id") id: String
    ):Call<ResponModel>

    @GET("transaksi/customer/midtrans/{id}")
    fun getRiwayatMidtrans(
        @Path("id") id: String
    ):Call<ResponModel>

    @GET("transaksi/customer/midtransdetail/{id}")
    fun getRiwayatTransaction(
        @Path("id") id: String
    ):Call<WrappedResponse<Midtrans>>
}