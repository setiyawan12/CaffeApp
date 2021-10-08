package yayang.setiyawan.caffe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_akun.view.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterProduk
import yayang.setiyawan.caffe.contract.ProductContract
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.presenter.ProductPresenter

class AkunFragment : Fragment(){
//    private lateinit var presenter: ProductContract.Presenter
//    lateinit var adapterProduk: AdapterProduk
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_akun, container, false)
//        presenter = ProductPresenter(this)
//        presenter?.getAllProductbyid()
        return view
    }

//    override fun attachToRecycler(listProduk: List<Produk>) {
//        view?.rv_id?.apply {
//            adapterProduk = AdapterProduk(requireActivity(),listProduk)
//            adapter = adapterProduk
//
//            val linearLayoutManager = LinearLayoutManager(activity)
//            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//            layoutManager = linearLayoutManager
//        }
//    }
}