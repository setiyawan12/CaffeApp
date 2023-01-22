package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_position.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.databinding.ActivityPositionBinding

class PositionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPositionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonSet()
    }
    private fun buttonSet() {
        binding.btnInside.setOnClickListener {
            startActivity(Intent(this,ScannerActivity::class.java))
        }
        binding.btnOutside.setOnClickListener {
            startActivity(Intent(this,MejaActivity::class.java))
        }
    }
}