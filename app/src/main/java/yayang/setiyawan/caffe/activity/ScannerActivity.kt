package yayang.setiyawan.caffe.activity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import kotlinx.android.synthetic.main.activity_scanner.*
import org.json.JSONException
import org.json.JSONObject
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.R

class ScannerActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPref
    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        sharedPref = SharedPref(this)
        setupPermissions()
        codeScanner()
    }
    private fun codeScanner() {
        codeScanner = CodeScanner(this, scn)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
            decodeCallback = DecodeCallback {
                runOnUiThread {
                    if (it.text == null){
                        Toast.makeText(this@ScannerActivity,"data kosong",Toast.LENGTH_SHORT).show()
                    }else{
                        try {
                            val obj = JSONObject(it.text)
                            sharedPref.setString(sharedPref.meja,obj.getString("meja"))
                            Toast.makeText(this@ScannerActivity,obj.getString("meja"),Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@ScannerActivity, MainActivity::class.java))
                        }catch (e: JSONException){
                            e.printStackTrace()
                        }
                    }
                }
            }
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "codeScanner: ${it.message}")
                }
            }
            scn.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to use this app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    companion object {
        private const val CAMERA_REQ = 101
    }

}