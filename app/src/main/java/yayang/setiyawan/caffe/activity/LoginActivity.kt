package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.contract.LoginActivityContract
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.User

class LoginActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}