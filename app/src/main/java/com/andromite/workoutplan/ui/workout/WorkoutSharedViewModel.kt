package com.andromite.workoutplan.ui.workout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.Utils
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutSharedViewModel @Inject constructor(var remoteConfig: FirebaseRemoteConfig) : ViewModel() {

    val workoutListLiveData = MutableLiveData<List<WorkoutListItem>?>()

    fun loadDefaultList(){
        if (workoutListLiveData.value == null){
            val string = remoteConfig[Enums.workouts.name].asString()
            val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)
            workoutListLiveData.postValue(template.list)
        }
    }

    fun getWorkoutList(): List<WorkoutListItem>? {
        return workoutListLiveData.value
    }

    fun updateWorkoutItem(type: String?, selectedWorkout: Workout, checked : Boolean) {
        if (workoutListLiveData.value != null) {
            for (item in workoutListLiveData.value!!){
                if (item.type.equals(type)){
                    val workoutList = item.workoutList
                    if (workoutList != null) {
                        for (workout in workoutList){
                            if (workout.name.equals(selectedWorkout.name)){
                                workout.checked = checked
                            }
                        }
                    }
                }
            }
        }
    }


}