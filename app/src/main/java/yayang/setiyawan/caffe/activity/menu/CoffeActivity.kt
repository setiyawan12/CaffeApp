package yayang.setiyawan.caffe.activity.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_coffe.*
import kotlinx.android.synthetic.main.activity_coffe.tv_empty
import kotlinx.android.synthetic.main.activity_snack.*
import kotlinx.android.synthetic.main.toolbar.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterFood
import yayang.setiyawan.caffe.contract.ProductCoffeContract
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductCoffePresenter

class CoffeActivity : AppCompatActivity(),ProductCoffeContract.View {
    private lateinit var presenter: ProductCoffeContract.Presenter
    lateinit var adapterFood: AdapterFood
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffe)
        Helper().setToolbar(this, toolbar, "Coffe")
        presenter = ProductCoffePresenter(this)
        presenter?.getProdukCoffe()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
        rv_coffe.apply {
            adapterFood = AdapterFood(this@CoffeActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager = LinearLayoutManager(this@CoffeActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }
    override fun emptydata(status: Boolean) {
        if (status){
            tv_empty?.visibility= View.VISIBLE
        }else{
            tv_empty?.visibility= View.GONE
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