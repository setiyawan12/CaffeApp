package yayang.setiyawan.caffe.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.OutsidePaymentActivity
import yayang.setiyawan.caffe.activity.PembayaranActivity
import yayang.setiyawan.caffe.app.Constant
import yayang.setiyawan.caffe.model.Meja

class AdapterMeja(var activity:Activity,var data:List<Meja>):RecyclerView.Adapter<AdapterMeja.Holder>() {
    class Holder (view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tv_status = view.findViewById<TextView>(R.id.v_nama)
        val layout = view.findViewById<View>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_meja,parent,false)
        return Holder(view)
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val nama = data[position].no_meja
        val status = data[position].status
        holder.tvNama.text = nama.toString()
        if (status == "Aktif"){
            holder.tv_status.text = "Di Tempati"
        }else{
            holder.tv_status.text = "Kosong"
        }
        holder.layout.setOnClickListener {
            if (status == "Aktif"){
                SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Meja Di Gunakan")
                    .show()
            }else{
                val activiti = Intent(activity,OutsidePaymentActivity::class.java)
                Constant.setIdMeja(activity,nama!!)
                activity.startActivity(activiti)
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}