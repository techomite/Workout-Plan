package com.andromite.workoutplan.ui.workout

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Enums
import com.andromite.workoutplan.utils.FireStoreUtils
import com.andromite.workoutplan.utils.WorkoutHelper
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutSharedViewModel @Inject constructor(var remoteConfig: FirebaseRemoteConfig) : ViewModel() {

    val workoutListLiveData = MutableLiveData<List<WorkoutListItem>>()

    fun loadDefaultList(listener : ()-> Unit){
        if (workoutListLiveData.value == null){
            val string = remoteConfig[Enums.workouts.name].asString()
            val template: WorkoutListResponse = Gson().fromJson(string, WorkoutListResponse::class.java)
            workoutListLiveData.postValue(template.list)
            listener.invoke()
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

    fun fetchListFromDB(stringExtra: String?, context: Context, listener : (List<WorkoutListItem>)-> Unit) {

        if (workoutListLiveData.value == null) {
            val string = remoteConfig[Enums.workouts.name].asString()
            val templateCategories: WorkoutListResponse =
                Gson().fromJson(string, WorkoutListResponse::class.java)

            if (stringExtra != null) {
                FireStoreUtils().readWorkoutList(context, stringExtra) {
                    val selectedWorkoutCategories = WorkoutHelper.getSelectedWorkoutList(it)

                    for (i in templateCategories.list.indices) {
                        Log.e("asdfasdf", "TemplateCategory pos: $i type name: ${templateCategories.list[i].type}")
                        for (j in selectedWorkoutCategories.indices) {
                            Log.e("asdfasdf", "     selectedWorkoutCategories pos: $j type name: ${selectedWorkoutCategories[j].type}")
                            if (templateCategories.list[i].type == selectedWorkoutCategories[j].type) {
                                Log.e("asdfasdf", "     FOUND")
                                for (a in templateCategories.list[i].workoutList.indices) {
                                    Log.e("asdfasdf", "         template workoutList pos: $a type name: ${templateCategories.list[i].workoutList[a].name} ")
                                    for (b in selectedWorkoutCategories[j].workoutList.indices) {
                                        Log.e("asdfasdf", "             selected workoutList pos: $b type name: ${selectedWorkoutCategories[j].workoutList[b].name} ")
                                        if (templateCategories.list[i].workoutList[a].name == selectedWorkoutCategories[j].workoutList[b].name) {
                                            Log.e("asdfasdf", "                FOUND")
                                            templateCategories.list[i].workoutList[a].checked = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                    workoutListLiveData.postValue(templateCategories.list)
                    listener.invoke(templateCategories.list)
                }
            }
        }
    }



    fun saveWorkToFirestoreDB() {

    }


}