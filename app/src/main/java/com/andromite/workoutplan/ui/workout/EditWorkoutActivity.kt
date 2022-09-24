package com.andromite.workoutplan.ui.workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andromite.workoutplan.databinding.ActivityEditWorkoutBinding
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okio.Utf8
import javax.inject.Inject

@AndroidEntryPoint
class EditWorkoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditWorkoutBinding

    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig
    lateinit var viewModel: WorkoutSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WorkoutSharedViewModel::class.java]
        viewModel.loadDefaultList()

        // display workout from remote config
        // get selected workout objects and save them in firebase firestore
        // view pager with fragments setup

        getWorkout()

        binding.saveButton.setOnClickListener {
            Utils.floge("save button final list: ${viewModel.getWorkoutList()}")
        }


    }

    private fun getWorkout() {
        val string = remoteConfig[Enums.workouts.name].asString()
        val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)

        template.list?.let { initViewPagerAndTabLayout(it) }
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