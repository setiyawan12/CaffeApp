package yayang.setiyawan.caffe.app

import android.content.Context

class Constant {
    companion object{
        fun getIdMeja(context: Context): Int {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            val id = pref?.getInt("ID", 0)
            return id!!
        }

        fun setIdMeja(context: Context, id: Int) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().apply {
                putInt("ID", id)
                apply()
            }
        }

        fun clearIdMeja(context: Context) {
            val pref = context.getSharedPreferences("ID", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }
    }
    }
