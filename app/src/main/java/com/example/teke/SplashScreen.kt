package com.example.teke

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import com.example.teke.Fragment.SignInFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val PREF_DATA = "user_room"
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        SplashLogo.startAnimation(topAnimation)
        textTSplash.startAnimation(topAnimation)
        textESplash.startAnimation(middleAnimation)
        textKSplash.startAnimation(bottomAnimation)
        textEESplash.startAnimation(topAnimation)

        TextSlogan.startAnimation(middleAnimation)

//        val splashScreenTimeOut = 3000
//        val intent = Intent(this, MainActivity::class.java)

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)

        val user = sp.getString("email", "")
        if (user == null || user == "") {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Dashboard::class.java))
                finish()
            }, 3000)
        }

//        Handler().postDelayed({
//
//            if (sharedpreferences!!.getBoolean("isLogin", true)) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                val intent = Intent(this, Dashboard::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }, 3000)
    }
}