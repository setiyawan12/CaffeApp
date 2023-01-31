package yayang.setiyawan.caffe.app

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import yayang.setiyawan.caffe.unit.Config
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun APIService(): ApiServices = getClient().create(ApiServices::class.java)
        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(Config.baseUrl).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            } else {
                retrofit!!
            }
        }
    }
    class Constant{
        companion object{
            const val BASE_URL = "https://e28c-36-68-54-208.ngrok.io/"
//            const val BASE_URL = "https://cafe-server-setiyawan.herokuapp.com/"
        }
    }
}