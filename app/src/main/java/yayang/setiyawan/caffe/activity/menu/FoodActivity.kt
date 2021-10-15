package yayang.setiyawan.caffe.activity.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_food.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterFood
import yayang.setiyawan.caffe.contract.ProductFoodContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductFoodPresenter

class FoodActivity : AppCompatActivity(),ProductFoodContract.View {
    private lateinit var presenter: ProductFoodContract.Presenter
    lateinit var adapterFood: AdapterFood
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        presenter = ProductFoodPresenter(this)
        presenter?.getProdukFood()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
        rv_food.apply {
            adapterFood = AdapterFood(this@FoodActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager= LinearLayoutManager(this@FoodActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }
    override fun emptydata(status: Boolean) {
        if (status){
            tv_empty.visibility=View.VISIBLE
        }else{
           tv_empty?.visibility = View.GONE
        }
    }
    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}