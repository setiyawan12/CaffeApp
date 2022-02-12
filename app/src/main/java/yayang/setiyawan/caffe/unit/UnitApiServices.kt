package yayang.setiyawan.caffe.unit

import retrofit2.Call
import retrofit2.http.*
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.model.ResponModel

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

    @GET("transaksi/customer/{id}")
    fun getRiwayat(
        @Path("id") id: String
    ):Call<ResponModel>
}