package yayang.setiyawan.caffe.activity.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterDetailHistory
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.DetailTransaksi
import yayang.setiyawan.caffe.model.Transaksi

class DetailTransaksiActivity : AppCompatActivity() {
    var transaksi = Transaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")
        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json,Transaksi::class.java)
        setData(transaksi)
        displayProduk(transaksi.details)
        Toast.makeText(this, "${transaksi.status}", Toast.LENGTH_SHORT).show()
    }
    fun setData(t: Transaksi) {
        tv_status.text = t.status
        val formatBaru = "dd MMMM yyyy, kk:mm:ss"
        tv_tgl.text = Helper().convertTanggal(t.created_at, formatBaru)
        tv_kodeUnik.text = Helper().gantiRupiah(t.kode_unik)
        tv_totalBelanja.text = Helper().gantiRupiah(t.total_harga)
        if (t.status != "MENUNGGU") div_footer.visibility = View.GONE
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
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}