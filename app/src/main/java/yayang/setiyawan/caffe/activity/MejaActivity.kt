package yayang.setiyawan.caffe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_meja.*
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.adapter.AdapterMeja
import yayang.setiyawan.caffe.contract.GetMejaActivityContract
import yayang.setiyawan.caffe.model.Meja
import yayang.setiyawan.caffe.presenter.AllMejaActivityPresenter

class MejaActivity : AppCompatActivity(),GetMejaActivityContract.AllMejaActivityView {
    private lateinit var presenter:GetMejaActivityContract.AllMejaPresenter
    lateinit var adapterMeja: AdapterMeja
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja)
        presenter = AllMejaActivityPresenter(this)
        presenter.allmeja()
    }
    override fun attachToRecycler(listMeja: List<Meja>) {
        recyclerView.apply {
            adapterMeja = AdapterMeja(this@MejaActivity,listMeja)
            adapter=adapterMeja
            val gridLayout = GridLayoutManager(this@MejaActivity,2)
            layoutManager = gridLayout
        }
    }
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onResume() {
        super.onResume()
        presenter.allmeja()
    }
}