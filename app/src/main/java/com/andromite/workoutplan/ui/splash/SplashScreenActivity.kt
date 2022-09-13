package com.andromite.workoutplan.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andromite.workoutplan.R
import com.andromite.workoutplan.ui.auth.SignInActivity
import com.andromite.workoutplan.ui.home.HomeActivity
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.SP
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (!SP.get(this, Enums.UserId.name).equals("0")) {
            diRemoteConfig()
        } else startActivity(Intent(this, SignInActivity::class.java))

    }

    private fun diRemoteConfig() {
//        val sampleJsonString = remoteConfig.get("workouts").asString()
//        val template: TemplateResponse =
//            Gson().fromJson(sampleJsonString, TemplateResponse::class.java)
//
//        for (i in 0 until template.size) {
//            Utils.floge("template item: name: ${template[i].name} type:${template[i].type} list of workouts: ${template[i].workoutList}")
//        }

        startActivity(Intent(this, HomeActivity::class.java))
    }

}