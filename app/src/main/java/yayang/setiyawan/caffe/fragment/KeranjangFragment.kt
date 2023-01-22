package yayang.setiyawan.caffe.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import yayang.setiyawan.caffe.adapter.AdapterKeranjang
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.*
import yayang.setiyawan.caffe.helper.SharedPref
import yayang.setiyawan.caffe.room.MyDatabase

class KeranjangFragment : Fragment() {
    private lateinit var myDb: MyDatabase
    private lateinit var s: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        myDb = MyDatabase.getInstance(requireActivity())!!
        s = SharedPref(requireActivity())
        mainButton()
        return view
    }
    lateinit var adapter: AdapterKeranjang
    var listProduk = ArrayList<Produk>()
    private fun displayProduk() {
        listProduk = myDb.daoKeranjang().getAll() as ArrayList
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapter = AdapterKeranjang(requireActivity(), listProduk, object : AdapterKeranjang.Listeners {
            override fun onUpdate() {
                hitungTotal()
            }
            override fun onDelete(position: Int) {
                listProduk.removeAt(position)
                adapter.notifyDataSetChanged()
//                with(adapter){notifyDataSetChanged()}
                hitungTotal()
            }
        })
        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }
    var totalHarga = 0
    fun hitungTotal() {
        val listProduk = myDb.daoKeranjang().getAll() as ArrayList
        totalHarga = 0
        var isSelectedAll = true
        for (produk in listProduk) {
            if (produk.selected) {
                val harga = Integer.valueOf(produk.harga.toString())
                totalHarga += (harga * produk.jumlah)
            } else {
                isSelectedAll = false
            }
        }
        cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun mainButton(){
        btnBayar.setOnClickListener {
            if (s.getStatusLogin()){
                var isThereProduk = false
                for (p in listProduk){
                    if (p.selected) isThereProduk = true
                }
                if (isThereProduk){
                    val intent = Intent(requireActivity(),PositionActivity::class.java)
                    intent.putExtra("extra",""+totalHarga)
                    startActivity(intent)
                }else{
                    SweetAlertDialog(requireActivity(),SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Tidak Ada Produk Yang Terpilih")
                        .show()
                }
            }else{
                requireActivity().startActivity(Intent(requireActivity(),CustomerActivity::class.java))
            }
        }
        btnDelete.setOnClickListener {
            val listDelete = ArrayList<Produk>()
            for (p in listProduk){
                if (p.selected) listDelete.add(p)
            }
            delete(listDelete)
            tvTotal.text = "Rp0,00"
        }
        cbAll.setOnClickListener {
            for (i in listProduk.indices){
                val produk = listProduk[i]
                produk.selected = cbAll.isChecked
                listProduk[i] = produk
            }
            adapter.notifyDataSetChanged()

//            with(adapter){notifyDataSetChanged()}
        }
    }
    private fun delete(data: ArrayList<Produk>) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listProduk.clear()
                listProduk.addAll(myDb.daoKeranjang().getAll() as ArrayList)
                adapter.notifyDataSetChanged()
//                with(adapter){notifyDataSetChanged()}
            })
    }

    private lateinit var btnDelete: ImageView
    private lateinit var rvProduk: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var btnBayar: TextView
    private lateinit var cbAll: CheckBox
    private fun init(view:View){
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }
    override fun onResume() {
        super.onResume()
        displayProduk()
        hitungTotal()
    }
}