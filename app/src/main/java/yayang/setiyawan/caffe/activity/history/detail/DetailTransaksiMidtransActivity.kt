package yayang.setiyawan.caffe.activity.history.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.activity_detail_transaksi.div_footer
import kotlinx.android.synthetic.main.activity_detail_transaksi.rv_produk
import kotlinx.android.synthetic.main.activity_detail_transaksi.tv_status
import kotlinx.android.synthetic.main.activity_detail_transaksi.tv_tgl
import kotlinx.android.synthetic.main.activity_detail_transaksi.tv_totalBelanja
import kotlinx.android.synthetic.main.activity_detail_transaksi_midtrans.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterDetailHistory
import yayang.setiyawan.caffe.contract.DetailHistoryTransactionMidtrans
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.*
import yayang.setiyawan.caffe.presenter.DetailTransactionMidtrans
import yayang.setiyawan.caffe.unit.UnitApiConfig

class DetailTransaksiMidtransActivity : AppCompatActivity(),DetailHistoryTransactionMidtrans.view {
    private lateinit var presenter:DetailHistoryTransactionMidtrans.presenter
    var transaksi = Transaksi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi_midtrans)
        presenter = DetailTransactionMidtrans(this)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")
        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json,Transaksi::class.java)
        setData(transaksi)
        getRek(transaksi)
        displayProduk(transaksi.details)
    }
    fun setData(t: Transaksi) {
        tv_status.text = t.status
        tv_order_id.text = t.order_id
        val formatBaru = "dd MMMM yyyy, kk:mm:ss"
        tv_tgl.text = Helper().convertTanggal(t.created_at, formatBaru)
        tv_totalBelanja.text = Helper().gantiRupiah(t.total_harga)
//        tv_order_id.text = t.order_id
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

    fun getRek(t: Transaksi){
        val id =t.order_id
        presenter.detailMidtrans(id,this)
//        UnitApiConfig.instanceRetrofit.getRiwayatTransaction(id).enqueue(object :Callback<WrappedResponse<Midtrans>>{
//            override fun onResponse(
//                call: Call<WrappedResponse<Midtrans>>,
//                response: Response<WrappedResponse<Midtrans>>
//            ) {
//                val body = response.body()
//                Toast.makeText(this@DetailTransaksiMidtransActivity, body?.data?.transaction_time, Toast.LENGTH_SHORT).show()
////                print("Hasil"+ body?.data?.va_numbers?.get(0)?.bank)
//                val result = body?.data
//                if (result?.payment_type == "bank_transfer"){
//                    tv_nomerRek.text = result.va_numbers.get(0).va_number
//                    tv_payment_type.text = result.va_numbers.get(0).bank
//                    tv_currency.text = result.currency
//                    tv_status_midtrans.text = result.transaction_status
//                }else if(result?.payment_type == "echannel"){
//                    tv_nomerRek.text = result.bill_key
//                    tv_payment_type.text = result.payment_type
//                    tv_currency.text = result.currency
//                    tv_status_midtrans.text = result.transaction_status
//                }else if(result?.payment_type == "cstore"){
//                    tv_nomerRek.text = result.payment_code
//                    tv_payment_type.text = result.store
//                    tv_currency.text = result.currency
//                    tv_status_midtrans.text = result.transaction_status
//                }else{
//                    tv_nomerRek.text="null"
//                    tv_payment_type.text="null"
//                    tv_status_midtrans.text = "null"
//                    tv_currency.text = "hull"
//                }
//            }
//
//            override fun onFailure(call: Call<WrappedResponse<Midtrans>>, t: Throwable) {
//                Toast.makeText(this@DetailTransaksiMidtransActivity, "Error", Toast.LENGTH_SHORT).show()
//            }
//
//        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun success(result: Midtrans) {
        if (result?.payment_type == "bank_transfer"){
            tv_nomerRek.text = result.va_numbers.get(0).va_number
            tv_payment_type.text = result.va_numbers.get(0).bank
            tv_currency.text = result.currency
            tv_status_midtrans.text = result.transaction_status
        }else if(result?.payment_type == "echannel"){
            tv_nomerRek.text = result.bill_key
            tv_payment_type.text = result.payment_type
            tv_currency.text = result.currency
            tv_status_midtrans.text = result.transaction_status
        }else if(result?.payment_type == "cstore"){
            tv_nomerRek.text = result.payment_code
            tv_payment_type.text = result.store
            tv_currency.text = result.currency
            tv_status_midtrans.text = result.transaction_status
        }else{
            tv_nomerRek.text="null"
            tv_payment_type.text="null"
            tv_status_midtrans.text = "null"
            tv_currency.text = "hull"
        }
    }

    override fun toast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}