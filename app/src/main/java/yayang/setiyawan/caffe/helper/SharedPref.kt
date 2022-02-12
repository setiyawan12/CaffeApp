package yayang.setiyawan.caffe.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import yayang.setiyawan.caffe.model.User

class SharedPref(activity: Activity) {
    val login = "login"
    val name = "nama"
    val email = "email"
    val id = 0
    val user = "user"
    val meja = ""
    val data = "data"
    val phone = "phone"
    val mypref = "MAIN_PRF"
    val sp: SharedPreferences
    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean) {
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin(): Boolean {
        return sp.getBoolean(login, false)
    }


    fun setUser(value: User) {
        val mydata: String = Gson().toJson(value, User::class.java)
        sp.edit().putString(data, mydata).apply()
    }

    fun getUser(): User? {
        val data:String = sp.getString(data, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }

    fun setString(key: String, vlaue: String) {
        sp.edit().putString(key, vlaue).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "")!!
    }

    fun clear(){
        sp.edit().clear().apply()
    }

}