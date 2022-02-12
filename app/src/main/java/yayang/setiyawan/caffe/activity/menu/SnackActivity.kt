package yayang.setiyawan.caffe.activity.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_snack.*
import kotlinx.android.synthetic.main.toolbar.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterFood
import yayang.setiyawan.caffe.contract.ProductSnackContract
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductSnackPresenter

class SnackActivity : AppCompatActivity(),ProductSnackContract.View {
    private lateinit var presenter: ProductSnackContract.Presenter
    lateinit var adapterFood: AdapterFood
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)
        Helper().setToolbar(this, toolbar, "Snack")
        presenter = ProductSnackPresenter(this)
        presenter?.getProdukSnack()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
        rv_snack.apply {
            adapterFood = AdapterFood(this@SnackActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager = LinearLayoutManager(this@SnackActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun emptydata(status: Boolean) {
        if (status){
            tv_empty?.visibility= View.VISIBLE
        }else{
            tv_empty?.visibility=View.GONE
        }
    }
    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}