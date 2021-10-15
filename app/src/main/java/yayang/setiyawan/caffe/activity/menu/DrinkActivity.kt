package yayang.setiyawan.caffe.activity.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_drink.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterFood
import yayang.setiyawan.caffe.contract.ProductDrinkContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductDrinkPresenter

class DrinkActivity : AppCompatActivity(),ProductDrinkContract.View {
    private lateinit var presenter: ProductDrinkContract.Presenter
    lateinit var adapterFood: AdapterFood
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)
        presenter = ProductDrinkPresenter(this)
        presenter?.getProdukDrink()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
        rv_drink.apply {
            adapterFood = AdapterFood(this@DrinkActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager = LinearLayoutManager(this@DrinkActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun emptydata(status: Boolean) {
        if (status){
            tv_empty?.visibility=View.VISIBLE
        }else{
            tv_empty?.visibility=View.GONE

        }
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}