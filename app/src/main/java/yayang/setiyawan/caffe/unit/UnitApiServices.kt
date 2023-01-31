package yayang.setiyawan.caffe.unit

import retrofit2.Call
import retrofit2.http.*
import yayang.setiyawan.caffe.model.*

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

    @GET("meja")
    fun Allmeja():Call<ListResponse<Meja>>

    @GET("meja/{id}")
    fun getMejas(
        @Path("id") id: String
    ):Call<WrappedResponseMeja<Meja>>
    @POST("chekout/batal/{id}")
    fun cancleTransaction(
        @Path("id")id:String
    ):Call<ResponModel>
}