package yayang.setiyawan.caffe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_tes.*


class TesActivity : AppCompatActivity(),View.OnClickListener{
    private var qrScan: IntentIntegrator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrScan = IntentIntegrator(this)
        btnScanQRCode.setOnClickListener(this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
           if (result != null) {
            //Check to see if QR Code has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //QR Code contains some data
                try {
                    //Convert the QR Code Data to JSON
                    val obj = JSONObject(result.contents)
                    //Set up the TextView Values using the data from JSON
                    txtFruitName.setText(obj.getString("meja"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format MisMatch of the JSON
                    Toast.makeText(this, "Scan ulang", Toast.LENGTH_LONG).show()
                    qrScan?.initiateScan()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(p0: View?) {
        qrScan?.setOrientationLocked(true)
        qrScan?.setBeepEnabled(true)
        qrScan?.setPrompt("tampilkan qrcode")
        qrScan?.initiateScan()

    }
}