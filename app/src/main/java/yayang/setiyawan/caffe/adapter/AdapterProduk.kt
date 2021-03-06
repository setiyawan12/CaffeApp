package yayang.setiyawan.caffe.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import yayang.setiyawan.caffe.activity.DetailProdukActivity
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Produk
import yayang.setiyawan.caffe.R




class AdapterProduk(var activity: Activity, var data:List<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>() {
    class Holder(view:View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout = view.findViewById<View>(R.id.layout)
        val stcokHabis = view.findViewById<View>(R.id.stockHabis)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk1,parent, false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder:Holder, position: Int) {
        val image = data[position].image
        val harga = data[position].harga
        val cekstock = data[position].stock
        holder.tvNama.text = data[position].name
        if (cekstock > 0 ){
            holder.tvHarga.text = Helper().gantiRupiah(harga.toString())
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.product)
                .error(R.drawable.product)
                .into(holder.imgProduk)
        }else{
            holder.tvHarga.text = "STOCK HABIS"
        }
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.imgProduk)
        holder.layout.setOnClickListener {
            if (cekstock > 0){
                val activiti = Intent(activity, DetailProdukActivity::class.java)
                val str = Gson().toJson(data[position],Produk::class.java)
                activiti.putExtra("extra",str)
                activity.startActivity(activiti)
            }else{
                holder.tvHarga.text = "STOCK HABIS"
                SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Product Sold Out")
                    .show()
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}