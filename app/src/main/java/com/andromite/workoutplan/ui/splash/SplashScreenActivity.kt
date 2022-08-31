package com.andromite.workoutplan.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andromite.workoutplan.R
import com.andromite.workoutplan.ui.auth.SignInActivity
import com.andromite.workoutplan.ui.home.HomeActivity
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.SP

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        
        if (!SP.get(this,Enums.UserId.name).equals("0")){
            startActivity(Intent(this, HomeActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        
    }
}