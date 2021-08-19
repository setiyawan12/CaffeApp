package yayang.setiyawan.caffe.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import yayang.setiyawan.caffe.Model.Produk
import yayang.setiyawan.caffe.R
class AkunFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_akun, container, false)
    }
}