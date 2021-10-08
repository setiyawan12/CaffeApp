package yayang.setiyawan.caffe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import yayang.setiyawan.caffe.adapter.AdapterProduk
import yayang.setiyawan.caffe.contract.ProductContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductPresenter
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.FoodActivity

class HomeFragment : Fragment(),ProductContract.View {
    private lateinit var presenter: ProductContract.Presenter
    lateinit var adapterProduk: AdapterProduk
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view :View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
    presenter = ProductPresenter(this)
    presenter?.getAllProduct()
        presenter?.getProductFood()
        presenter?.getProductSnack()
    return  view
    }
    override fun attachToRecycler(listProduk: List<Produk>) {
        view?.rv_produk?.apply {
            adapterProduk = AdapterProduk(requireActivity(),listProduk)
            adapter = adapterProduk
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun attachToRecyclerFood(listProduk: List<Produk>) {
        view?.rv_food?.apply {
            adapterProduk = AdapterProduk(requireActivity(),listProduk)
            adapter = adapterProduk
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun attachToRecyclerSnack(listProduk: List<Produk>) {
        view?.rv_snack?.apply {
            adapterProduk = AdapterProduk(requireActivity(),listProduk)
            adapter = adapterProduk
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
        }
    }

    override fun emptydata(status: Boolean) {
        if (status){
            view?.tv_empty?.visibility = View.VISIBLE
            view?.tv_snack?.visibility = View.GONE
            view?.tv_menu?.visibility = View.GONE
            view?.tv_food?.visibility = View.GONE
        }else{
            view?.tv_empty?.visibility = View.GONE
            view?.tv_snack?.visibility = View.VISIBLE
            view?.tv_menu?.visibility = View.VISIBLE
            view?.tv_food?.visibility = View.VISIBLE
        }
    }

    override fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    fun init (view: View){
        val btn_food = view.findViewById<ImageView>(R.id.food)
        btn_food.setOnClickListener {
            startActivity(Intent(activity,FoodActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        presenter?.getAllProduct()
        presenter?.getProductFood()
        presenter?.getProductSnack()
    }
}