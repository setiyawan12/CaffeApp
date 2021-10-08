package yayang.setiyawan.caffe.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref (contex : Context){
    private val PREF_NAME = "sharedPref"
    private var sharedPref:SharedPreferences
    private val editor:SharedPreferences.Editor

    init {
        sharedPref = contex.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put (key:String, value:String){
        editor.putString(key,value)
            .apply()
    }
    fun getString(key:String):String?{
        return sharedPref.getString(key,null)
    }
    fun put(key:String,value:Boolean){
        editor.putBoolean(key,value)
            .apply()
    }
    fun getBoolean(key: String):Boolean{
        return sharedPref.getBoolean(key,false)
    }
}