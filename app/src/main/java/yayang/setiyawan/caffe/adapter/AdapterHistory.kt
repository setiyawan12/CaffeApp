package yayang.setiyawan.caffe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.Helper
import yayang.setiyawan.caffe.model.Transaksi

class AdapterHistory(var data:ArrayList<Transaksi>,var listener:Listeners):RecyclerView.Adapter<AdapterHistory.Holder>() {
    class Holder(view:View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val tvTangal = view.findViewById<TextView>(R.id.tv_tgl)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val btnDetail = view.findViewById<TextView>(R.id.btn_detail)
        val layout = view.findViewById<CardView>(R.id.layout)
    }
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        context = parent.context
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_history,parent,false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]
        val name = a.details[0].produk.name
        holder.tvNama.text = name
        holder.tvHarga.text = Helper().gantiRupiah(a.total_harga)
        holder.tvJumlah.text = a.total_item + " Items"
        holder.tvStatus.text = a.status
        val formatBaru = "d MMM yyyy"
        holder.tvTangal.text = Helper().convertTanggal(a.created_at, formatBaru)
        var color = context.getColor(R.color.menungu)
        if (a.status == "SELESAI") color = context.getColor(R.color.selesai)
        else if (a.status == "BATAL") color = context.getColor(R.color.batal)
        holder.tvStatus.setTextColor(color)
        holder.layout.setOnClickListener {
            listener.onClicked(a)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
    interface Listeners {
        fun onClicked(data: Transaksi)
    }
}