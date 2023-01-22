package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import yayang.setiyawan.caffe.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
//            val intent = Intent(this,MainActivity::class.java)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
    }
