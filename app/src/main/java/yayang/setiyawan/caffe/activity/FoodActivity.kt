package yayang.setiyawan.caffe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_food.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterFood
import yayang.setiyawan.caffe.contract.ProductContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductPresenter

class FoodActivity : AppCompatActivity(),ProductContract.View {
    private lateinit var presenter: ProductPresenter
    lateinit var adapterFood: AdapterFood
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        presenter = ProductPresenter(this)
        presenter?.getProductFood()
    }

    override fun attachToRecycler(listProduk: List<Produk>) {
       /* rv_food.apply {
            adapterFood = AdapterFood(this@FoodActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager = LinearLayoutManager(this@FoodActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }*/
    }

    override fun attachToRecyclerFood(listProduk: List<Produk>) {
        rv_food.apply {
            adapterFood = AdapterFood(this@FoodActivity,listProduk)
            adapter = adapterFood
            val linearLayoutManager = LinearLayoutManager(this@FoodActivity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun attachToRecyclerSnack(listProduk: List<Produk>) {
        TODO("Not yet implemented")
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