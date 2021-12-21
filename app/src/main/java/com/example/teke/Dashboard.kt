package com.example.teke

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.teke.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var editor: SharedPreferences.Editor
    private val MyPREFERENCES = "MyPrefs"
    val PREF_DATA = "user_room"
    lateinit var sp: SharedPreferences
    var currentId: Int = 0
    var currentName: String = ""
    var currentEmail: String = ""
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null

    @SuppressLint("RtlHardcoded", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.TOOLBAR_MENU))

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment?.navController

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)
        editor = sp.edit()
        val first = sp.getBoolean("first", false)
        currentName = sp.getString("name", "").toString()
        currentEmail = sp.getString("email", "").toString()

        binding.ham.setOnClickListener {
            da.openDrawer(Gravity.LEFT)
        }

        binding.imageCartDashboard.setOnClickListener {
            startActivity(Intent(this@Dashboard, CartActivity::class.java))
        }

        toggle =
            ActionBarDrawerToggle(
                this,
                binding.da,
                R.string.open,
                R.string.close
            )
        binding.da.addDrawerListener(toggle)
        toggle.syncState()

        binding.startNav.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.nav_contactus -> {
                    navController?.navigate(R.id.contactUsFragment)
                }

                R.id.home_menu -> {
                    navController?.navigate(R.id.dashboardFragment)
                }

                R.id.nav_account -> {
                    navController?.navigate(R.id.profileFragment)
                }

                R.id.nav_order -> {
                    navController?.navigate(R.id.myOrderFragment)
                }

                R.id.nav_cart -> {
                    startActivity(Intent(this@Dashboard, CartActivity::class.java))
                }

                R.id.nav_wishlist -> {
                    navController?.navigate(R.id.wishlistFragment)
                }

                R.id.nav_logout -> {
                    editor.putString("email", "")
                    editor.putBoolean("first", false)
                    editor.commit()
                    startActivity(Intent(this@Dashboard, MainActivity::class.java))
                    finish()
                }
            }
            da.closeDrawer(Gravity.LEFT)
            true
        }

        val sharedpreferences =
            getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)

        if (sharedpreferences.getBoolean("isLogin", false)) Toast.makeText(
            this,
            "Hello  " + sharedpreferences.getString("emailKey", "passKey"),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBackPressed() {
        if (da.isDrawerOpen(GravityCompat.START)) {
            da.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}