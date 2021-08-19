package yayang.setiyawan.caffe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yayang.setiyawan.caffe.Adapter.AdapterProduk
import yayang.setiyawan.caffe.Model.Produk
import yayang.setiyawan.caffe.Model.ResponModel
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.app.ApiConfig


class HomeFragment : Fragment() {
    lateinit var rv_Produk:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view :View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()
        return  view
    }
    private var listProduk:ArrayList<Produk> = ArrayList()
    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object: Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if(res.success == 1) {
                    val arrayProduk = ArrayList<Produk>()
                    for (p in res.produks) {
                        arrayProduk.add(p)
                    }
                    listProduk = arrayProduk
                    showProduk()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(requireActivity(), "Tidbisa koneksi ke server", Toast.LENGTH_LONG).show()
            }

        })
    }
    fun showProduk(){
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        view?.rv_produk?.adapter = AdapterProduk(requireActivity(),listProduk)
        view?.rv_produk?.layoutManager = layoutManager
    }
    fun init (view: View){
        rv_Produk = view.findViewById(R.id.rv_produk)

    }

    override fun onResume() {
        super.onResume()
        getProduk()
    }
}