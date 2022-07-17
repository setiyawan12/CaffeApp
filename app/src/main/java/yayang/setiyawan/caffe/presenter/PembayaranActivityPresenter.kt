package yayang.setiyawan.caffe.presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.activity.SuccessPembayaranActivity
import yayang.setiyawan.caffe.app.ApiConfig
import yayang.setiyawan.caffe.contract.PembayaranActivityContract
import yayang.setiyawan.caffe.model.Chekout
import yayang.setiyawan.caffe.model.ResponModel
import yayang.setiyawan.caffe.model.Transaksi
import yayang.setiyawan.caffe.room.MyDatabase

class PembayaranActivityPresenter(v: PembayaranActivityContract.View): PembayaranActivityContract.Presenter {
    private var view: PembayaranActivityContract.View? = v
    val webservices = ApiConfig.APIService()
    override fun bayarCash(context: Context, body: Chekout) {
        val request = webservices.chekout(body)
        request.enqueue(object : Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()
                if (respon?.success == 1) {
                    val jsTransaksi = Gson().toJson(respon?.transaksi, Transaksi::class.java)
                    val jsCheckout = Gson().toJson(body, Chekout::class.java)
                    val intent = Intent(context, SuccessPembayaranActivity::class.java)
                    intent.putExtra("transaksi", jsTransaksi)
                    intent.putExtra("checkout", jsCheckout)
                    view?.successBayarCash(intent)
                } else {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun bayarCashless(context: Context, body: Chekout) {
        val request = webservices.checkoutcashless(body)
        request.enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!
                Log.d("MIDTRANS",respon.data.toString())
                if (respon.success == 1){
                    val myDb = MyDatabase.getInstance(context)
                    for (produk in body.produks){
                        myDb?.daoKeranjang()?.deleteById(produk.id)
                    }
                    Toast.makeText(context,"Pembayaran Berhasil",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Gagal",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
            }

        })
    }
}