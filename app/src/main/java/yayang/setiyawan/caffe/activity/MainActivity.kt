package yayang.setiyawan.caffe.activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import yayang.setiyawan.caffe.fragment.AkunFragment
import yayang.setiyawan.caffe.fragment.HomeFragment
import yayang.setiyawan.caffe.fragment.KeranjangFragment
import yayang.setiyawan.caffe.R

class MainActivity : AppCompatActivity() {
    private val fragmentHome:Fragment = HomeFragment()
    private val fragmentKeranjang:Fragment=KeranjangFragment()
    private val fragmentAkun:Fragment= AkunFragment()
    private val fm : FragmentManager = supportFragmentManager
    private var active:Fragment = fragmentHome
    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(HomeFragment())
        setUpBottomView()
//        mainButton()
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            Log.d("respn fcm",token.toString())
//            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//        })

    }
    private fun setUpBottomView(){
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
    private fun mainButton(){
//        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
//        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()
//        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()
//
//        bottomNavigationView = findViewById(R.id.bottom_navigation)
//        menu = bottomNavigationView.menu
//        menuItem = menu.getItem(0)
//        menuItem.isChecked = true
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when(item.itemId){
//                R.id.navigation_home->{
//                    callFragment(0,fragmentHome)
//                }
//                R.id.navigation_keranjang->{
//                    callFragment(2,fragmentKeranjang)
//                }
//                R.id.navigation_akun->{
//                    callFragment(3,fragmentAkun)
//                }
//            }
//            false
//        }
    }
    private fun callFragment(int: Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
    private fun setFragment(fragment: Fragment){
        supportActionBar
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutHome, fragment)
            commit()
        }
    }
}