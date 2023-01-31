package yayang.setiyawan.caffe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_success_pembayaran.*
import kotlinx.android.synthetic.main.toolbar.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.history.HistoryActivity
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.room.MyDatabase

class SuccessPembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_pembayaran)
        Helper().setToolbar(this, toolbar, "Order Detail")
        mainButton()
        setValues()
    }
    private fun setValues(){
        val jsTransaksi = intent.getStringExtra("transaksi")
        val jsChekout = intent.getStringExtra("checkout")
        val transaksi = Gson().fromJson(jsTransaksi,Transaksi::class.java)
        val checkout = Gson().fromJson(jsChekout,Chekout::class.java)

        tv_customer.text = transaksi.name
        tv_kode_unik.text = transaksi.kode_unik.toString()
        tv_kode_payment.text = transaksi.kode_payment
        tv_meja.text = transaksi.meja
        tv_status.text= transaksi.status
        tv_total.text = transaksi.total_harga
        tv_total_item.text = transaksi.total_item

        val myDb = MyDatabase.getInstance(this)
        for (produk in checkout.produks){
            myDb?.daoKeranjang()?.deleteById(produk.id)
        }
    }
    private fun mainButton(){
        btn_cekStatus.setOnClickListener {
            startActivity(Intent(this,HistoryActivity::class.java))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}