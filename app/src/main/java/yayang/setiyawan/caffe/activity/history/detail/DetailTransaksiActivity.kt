package yayang.setiyawan.caffe.activity.history.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.HistoryActivity
import yayang.setiyawan.caffe.adapter.AdapterDetailHistory
import yayang.setiyawan.caffe.contract.CancleTransactionContract
import yayang.setiyawan.caffe.fragment.AkunFragment
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.DetailTransaksi
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.presenter.CancleTransactionPresenter

class DetailTransaksiActivity : AppCompatActivity(),CancleTransactionContract.CancleTransactionContractView {
    private lateinit var presenter: CancleTransactionContract.CancleTransactionContractPresenter
    var transaksi = Transaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        BottomSheetBehavior.from(sheet).apply {
            peekHeight = 200
            this.state =BottomSheetBehavior.STATE_COLLAPSED
        }
        Helper().setToolbar(this, toolbar, "Detail Transaksi")
        presenter = CancleTransactionPresenter(this)
        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json,Transaksi::class.java)
        setData(transaksi)
        displayProduk(transaksi.details)
        doQrScan()
        doCancle()
        Toast.makeText(this, "${transaksi.status}", Toast.LENGTH_SHORT).show()
    }

    private fun doCancle() {
        btnBatal.setOnClickListener {
            val message:String? = "Apakah Anda Yakin Membatalkan Pesanan"
            showCustomDialogCancle(message)
        }
    }

    private fun showCustomDialogCancle(message: String?) {
        val dialog = Dialog(this)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.layout_custom_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
        val tvMessage:TextView = dialog.findViewById(R.id.tvMessage)
        val btnYes:Button = dialog.findViewById(R.id.btnYes)
        val btnNo:Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message
        btnYes.setOnClickListener {
            val id = transaksi.id.toString()
            cancleTransation(id)
            onBackPressed()
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun doQrScan() {
        btnScanQr.setOnClickListener {
            val kode = transaksi.kode_unik.toString()
            showCustomDialogQr(kode)
        }
    }

    private fun showCustomDialogQr(kode: String) {
        if (kode.isEmpty()){
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show()
        }else{
            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(kode,BarcodeFormat.QR_CODE,712,712)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
                for (x in 0 until  width){
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

    fun setData(t: Transaksi) {
        tv_status.text = t.status
        val formatBaru = "dd MMMM yyyy, kk:mm:ss"
        tv_tgl.text = Helper().convertTanggal(t.created_at, formatBaru)
        tv_kode_trx.text = t.kode_trx
        tv_nama.text =t.name
        tv_kode_payment.text = t.kode_payment
        tv_payment_method.text = t.payment_method
        tv_meja.text = t.meja
        tv_total_item.text = t.total_item
        tv_kode_unik.text = t.kode_unik.toString()
        tv_total.text = Helper().gantiRupiah(t.total_harga)

        if (t.status != "MENUNGGU"){
            btnScanQr.visibility = View.VISIBLE
        }else{
            btnBatal.visibility = View.VISIBLE
        }
        var color = getColor(R.color.menungu)
        if (t.status == "SELESAI") color = getColor(R.color.selesai)
        else if (t.status == "BATAL") color = getColor(R.color.batal)
        tv_status.setTextColor(color)
    }
    fun displayProduk(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterDetailHistory(transaksis)
        rv_produk.layoutManager = layoutManager
    }

    fun cancleTransation(id:String){
        presenter.cancle(id)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}