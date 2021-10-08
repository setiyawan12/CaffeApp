package yayang.setiyawan.caffe.model

data class ListResponse<T>(
    val message: String,
    val data: List<T>,
    val success: Boolean
)