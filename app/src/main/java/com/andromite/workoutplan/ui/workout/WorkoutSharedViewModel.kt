package com.andromite.workoutplan.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Enums
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutSharedViewModel @Inject constructor(var remoteConfig: FirebaseRemoteConfig) : ViewModel() {

    val workoutList = MutableLiveData<WorkoutListResponse>()
    var selectedWorkoutList :  List<WorkoutListItem>? = ArrayList()


    fun getWorkoutList() {
        val string = remoteConfig[Enums.workouts.name].asString()
        val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)
        selectedWorkoutList = template.list
        workoutList.postValue(template)
    }


}