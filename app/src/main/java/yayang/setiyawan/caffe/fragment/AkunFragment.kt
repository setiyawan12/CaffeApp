package yayang.setiyawan.caffe.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.activity.RiwayatActivity
import yayang.setiyawan.caffe.activity.TestLoginActivity
import yayang.setiyawan.caffe.helper.SharedPref

class AkunFragment : Fragment(){
    private lateinit var tvNama:TextView
    private lateinit var tvPhone:TextView
    private lateinit var tvEmail:TextView
    lateinit var s:SharedPref
    private lateinit var btnLogout:ConstraintLayout
    private lateinit var btnRiwayat: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_akun, container, false)
        s = SharedPref(requireActivity())
        init(view)
        setData()
        mainButton()
        return view
    }
    fun setData(){
//        if (s.getUser() == null) {
//            val intent = Intent(activity, TestLoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            return
//        }
        tvNama.text = s.getString(s.name)
    }
    private fun init(view: View){
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tv_nama)
        btnRiwayat = view.findViewById(R.id.btn_riwayat)
    }
    private fun mainButton(){
        btnLogout.setOnClickListener {
            s.setStatusLogin(false)
            val intent = Intent(activity, TestLoginActivity::class.java)
            startActivity(intent)
        }
        btnRiwayat.setOnClickListener {
            val intent = Intent(activity,RiwayatActivity::class.java)
            startActivity(intent)
        }
    }
}