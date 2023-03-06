package com.andromite.workoutplan.ui.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andromite.workoutplan.databinding.ActivityEditWorkoutBinding
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.FireStoreUtils
import com.andromite.workoutplan.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class EditWorkoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditWorkoutBinding

    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig
    lateinit var viewModel: WorkoutSharedViewModel
    lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WorkoutSharedViewModel::class.java]
//        viewModel.loadDefaultList()

        initListener()

        val type = intent.getStringExtra("type")

        if (type.equals("new")) {
            // show default workout list
            date = Utils().getFormattedDate(Date())
            viewModel.loadDefaultList() {
                getDefaultWorkout()
            }

        } else if (type.equals("update")) {
            // fetch workout from db
            date = intent.getStringExtra("date")!!
            viewModel.fetchListFromDB(intent.getStringExtra("date"), this) {
                getCustomWorkout(it)
            }
        }
    }


    private fun initListener() {
        binding.saveButton.setOnClickListener {
            Utils.floge("save button final list: ${viewModel.getWorkoutList()}")
            val gson = Gson()
            val string = gson.toJson(viewModel.getWorkoutList())
            FireStoreUtils().addOrUpdateWorkoutList(this, Utils().getFormattedDate(Date()),string) {
                if (it == "success") {
                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                }
            }


        }
    }

    private fun getDefaultWorkout() {
        val string = remoteConfig[Enums.workouts.name].asString()
        val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)

        initViewPagerAndTabLayout(template.list)
    }

    private fun getCustomWorkout(list: List<WorkoutListItem>) {
//        val string = remoteConfig[Enums.workouts.name].asString()
//        val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)
//
//        template.list?.let { initViewPagerAndTabLayout(it) }
        Log.e("asdfasdf", "inside getCustomWorkout $list")
        initViewPagerAndTabLayout(list)
    }

    private fun initViewPagerAndTabLayout(list: List<WorkoutListItem>) {
        val adapter = WorkoutPagerAdapter(this)

        for (item in list) {
            adapter.add(WorkoutFragment(item.type, viewModel))
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(item.type))
        }


        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = list[position].type
        }.attach()
    }
}