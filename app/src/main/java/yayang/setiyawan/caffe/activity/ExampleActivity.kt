package yayang.setiyawan.caffe.activity

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.databinding.ActivityExampleBinding

class ExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight=200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
//        cancleButton()
        settingQr()
    }

    private fun settingQr() {
        binding.btnNo.setOnClickListener {
            val qr = "dada"
            showCustomDialogBox(qr)
        }
    }
    private fun showCustomDialogBox(message:String){
        if (message.isEmpty()){
            Toast.makeText(this, "enter some data", Toast.LENGTH_SHORT).show()
        }else{
            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(message, BarcodeFormat.QR_CODE,512,512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                for (x in 0 until width){
                    for (y in 0 until  width){
                        bmp.setPixel(x,y,if (bitMatrix[x,y])Color.BLACK else Color.WHITE)
                    }
                }
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.custom_dialog_qr)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val imageView = dialog.findViewById<ImageView>(R.id.qrImage)
                imageView.setImageBitmap(bmp)
                dialog.show()
            }catch (e:WriterException){
                e.printStackTrace()
            }
        }
    }
//    private fun cancleButton() {
//        binding.btnNo.setOnClickListener {
//            val message:String? = "Are you sure you want to log out"
//            showCustomDialogBox(message)
//        }
//    }

//    private fun showCustomDialogBox(message: String?){
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.layout_custom_dialog)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val tvMessage:TextView = dialog.findViewById(R.id.tvMessage)
//        val btnYes : Button = dialog.findViewById(R.id.btnYes)
//        val btnNo : Button = dialog.findViewById(R.id.btnNo)
//
//        tvMessage.text = message
//        btnYes.setOnClickListener {
//            Toast.makeText(this, "log out berhasil", Toast.LENGTH_SHORT).show()
//        }
//        btnNo.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
}