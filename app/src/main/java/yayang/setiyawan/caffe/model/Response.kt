package yayang.setiyawan.caffe.model

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    val message: String,
    val data: List<T>,
    val success: Boolean
)
data class WrappedResponse<T>(
    @SerializedName("status") var status : String,
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : T
)

data class WrappedResponseMeja<T>(
    @SerializedName("success") var success : Int,
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : T
)