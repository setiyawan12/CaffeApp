package yayang.setiyawan.caffe.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_customer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.unit.UnitApiConfig

class CustomerActivity : AppCompatActivity() {
    lateinit var s:SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        s = SharedPref(this)
        btnLogin.setOnClickListener {
            val customer = et_email.text.toString().trim()
            if (customer.isNotEmpty()){
                customer()
                progres()
            }else{
                SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("from harus di isi")
                    .show()
            }
        }
    }
    private fun progres(){
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading..."
        pDialog.setCancelable(false)
        pDialog.show()
    }
    fun customer(){
        UnitApiConfig.instanceRetrofit.customer(et_email.text.toString()).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                if (response.isSuccessful){
                    val body = response.body()
                    print("ini body" +body)
                    if (body != null){
                        s.setString(s.id.toString(),body.data.id.toString())
                        s.setString(s.name,body.data.name)
                        s.setStatusLogin(true)
                        val intent = Intent(this@CustomerActivity,MainActivity::class.java)
                        Toast.makeText(this@CustomerActivity,"Bayar Ulang",Toast.LENGTH_SHORT).show()
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@CustomerActivity, "Error:" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@CustomerActivity, "Error:" , Toast.LENGTH_SHORT).show()
            }

        })
    }
}