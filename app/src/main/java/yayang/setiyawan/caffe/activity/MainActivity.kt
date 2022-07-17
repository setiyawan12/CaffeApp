package yayang.setiyawan.caffe.activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import yayang.setiyawan.caffe.fragment.AkunFragment
import yayang.setiyawan.caffe.fragment.HomeFragment
import yayang.setiyawan.caffe.fragment.KeranjangFragment
import yayang.setiyawan.caffe.R
import yayang.setiyawan.caffe.helper.SharedPref
import android.content.pm.PackageManager
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var fragmentHome:Fragment = HomeFragment()
    private var fragmentKeranjang:Fragment = KeranjangFragment()
    private var fragmentAkun:Fragment = AkunFragment()
    private var fm:FragmentManager = supportFragmentManager
    private var active:Fragment = fragmentHome
    private lateinit var s:SharedPref
    private var statusLogin = false
    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView
    private var dariDetail: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setFragment(HomeFragment())
//        setUpBottomView()
        setUpBottomView()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, IntentFilter("event:keranjang"))
    }

    val mMessage: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            dariDetail = true
        }
    }
    fun setUpBottomView(){
        fm.beginTransaction().add(R.id.container,fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container,fragmentKeranjang).hide(fragmentKeranjang).commit()
        fm.beginTransaction().add(R.id.container,fragmentAkun).hide(fragmentAkun).commit()

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home->{
                    callFragment(0,fragmentHome)
                }
                R.id.navigation_keranjang->{
                    callFragment(1,fragmentKeranjang)
                }
                R.id.navigation_akun->{
                    callFragment(2,fragmentAkun)
                }
            }
            false
        }
    }
    private  fun callFragment(int:Int,fragment: Fragment){
        menuItem= menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment

    }
    override fun onResume() {
        if (dariDetail) {
            dariDetail = false
            callFragment(1, fragmentKeranjang)
        }
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val res = checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
            if (res != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 123)
            }
        }
    }

    var REQUEST_CODE_ASK_PERMISSIONS = 1002;

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode === REQUEST_CODE_ASK_PERMISSIONS){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "READ_PHONE_STATE Denied", Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }
}