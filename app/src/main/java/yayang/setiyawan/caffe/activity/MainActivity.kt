package yayang.setiyawan.caffe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import yayang.setiyawan.caffe.fragment.AkunFragment
import yayang.setiyawan.caffe.fragment.HomeFragment
import yayang.setiyawan.caffe.fragment.KeranjangFragment
import yayang.setiyawan.caffe.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(HomeFragment())
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home ->{
                    setFragment(HomeFragment())
                }
                R.id.navigation_keranjang ->{
                    setFragment(KeranjangFragment())
                }
                R.id.navigation_akun ->{
                    setFragment(AkunFragment())
                }
            }
            true
        }
    }
    private fun setFragment(fragment: Fragment){
        supportActionBar
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutHome, fragment)
            commit()
        }
    }
}