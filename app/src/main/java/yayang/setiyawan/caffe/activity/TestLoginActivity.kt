package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.unit.UnitApiConfig

class TestLoginActivity : AppCompatActivity() {
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_login)
        s= SharedPref(this)
        btnLogin.setOnClickListener {
            login()
        }
    }

    fun login(){
        UnitApiConfig.instanceRetrofit.login(et_email.text.toString(), etPassword.text.toString()).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        s.setStatusLogin(true)
                        s.setString(s.id.toString(),body.data.id.toString())
                        s.setString(s.name,body.data.name)
                        s.setString(s.email,body.data.email)
                        Toast.makeText(this@TestLoginActivity,"dapat id ${body.data.id}",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@TestLoginActivity,MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@TestLoginActivity, "Error:" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@TestLoginActivity, "Error:" , Toast.LENGTH_SHORT).show()
            }
        }
        )
    }
}