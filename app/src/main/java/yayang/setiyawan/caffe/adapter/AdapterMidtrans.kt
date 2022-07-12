package yayang.setiyawan.caffe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.model.Midtrans

class AdapterMidtrans(var data: List<Midtrans>):RecyclerView.Adapter<AdapterMidtrans.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val transaction_time = view.findViewById<TextView>(R.id.tv_status)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Holder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_midtrans,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = data[position]
        holder.transaction_time.text = a.transaction_time
    }

    override fun getItemCount(): Int {
       return data.size
    }
}