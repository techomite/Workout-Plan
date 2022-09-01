package com.andromite.workoutplan.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andromite.workoutplan.R
import com.andromite.workoutplan.network.models.TemplateResponse
import com.andromite.workoutplan.ui.auth.SignInActivity
import com.andromite.workoutplan.ui.home.HomeActivity
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.SP
import com.andromite.workoutplan.utils.Utils
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        
        if (!SP.get(this,Enums.UserId.name).equals("0")){
           callRemoteConfig()
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        
    }

    fun callRemoteConfig() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        val sameleJsonString = remoteConfig.get("sample_workout_list").asString()
//        Utils.floge("sample workout json from RC: ${sameleJsonString}")

        val template: TemplateResponse = Gson().fromJson(sameleJsonString, TemplateResponse::class.java)

        for (i in 0..template.size-1) {
            Utils.floge("template item: name: ${template.get(i).name} type:${template.get(i).type} list of workouts: ${template.get(i).workoutList}")
        }


            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val updated = task.result
                        Utils.floge("Config params updated: $updated")
                        Toast.makeText(
                            this, "Fetch and activate succeeded",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this, "Fetch failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                }


        }
    }