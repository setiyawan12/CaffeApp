package yayang.setiyawan.caffe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_get_user.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.SharedPref

class GetUserActivity : AppCompatActivity() {
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user)
        s = SharedPref(this)
//        val user = s.getString(s.email)
        tv_email.text = s.getString(s.email)
    }
}