package yayang.setiyawan.caffe.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_dialog.*
import yayang.setiyawan.caffe.adapter.AdapterProduk
import yayang.setiyawan.caffe.contract.ProductContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductPresenter
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.menu.CoffeActivity
import yayang.setiyawan.caffe.activity.menu.DrinkActivity
import yayang.setiyawan.caffe.activity.menu.FoodActivity
import yayang.setiyawan.caffe.activity.menu.SnackActivity

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
        presenter?.getAllFood()
        presenter?.getAllSnack()
        checkConnection()
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
//            view?.tv_snack?.visibility = View.VISIBLE
//            view?.tv_menu?.visibility = View.VISIBLE
//            view?.tv_food?.visibility = View.VISIBLE
        }else{
//            view?.tv_snack?.visibility = View.GONE
//            view?.tv_menu?.visibility = View.GONE
//            view?.tv_food?.visibility = View.GONE
        }
    }

    override fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    fun init (view: View){
        val btn_food = view.findViewById<ImageView>(R.id.food)
        btn_food.setOnClickListener {
            startActivity(Intent(activity, FoodActivity::class.java))
        }
        val btn_snack = view.findViewById<ImageView>(R.id.img_snack)
        btn_snack.setOnClickListener {
            startActivity(Intent(activity, SnackActivity::class.java))
        }
        val btn_coffe = view.findViewById<ImageView>(R.id.img_coffe)
        btn_coffe.setOnClickListener {
            startActivity(Intent(activity,CoffeActivity::class.java))
        }
        val btn_drink = view.findViewById<ImageView>(R.id.img_drink)
        btn_drink.setOnClickListener {
            startActivity(Intent(activity,DrinkActivity::class.java))
        }
    }
    private fun checkConnection(){
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.getNetworkCapabilities(manager.activeNetwork)
        if (null != networkInfo){
            print(true)
        }else{
            val dialog = Dialog(requireActivity())
            dialog.setContentView(R.layout.item_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.coba.setOnClickListener {
                recreate(requireActivity())
            }
            dialog.show()
        }
    }
    override fun onResume() {
        super.onResume()
        presenter?.getAllProduct()
        presenter?.getAllFood()
        presenter?.getAllSnack()
        checkConnection()
    }
}