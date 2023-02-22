package com.andromite.workoutplan.utils

import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.google.gson.Gson

class WorkoutHelper {
    companion object {

        fun getSelectedWorkoutList(jsonString: String?): ArrayList<WorkoutListItem> {
            val gson = Gson()
            val finalWorkoutItemList = ArrayList<WorkoutListItem>()

            if (jsonString != null) {
                val response: List<WorkoutListItem> =
                    gson.fromJson(jsonString, Array<WorkoutListItem>::class.java).asList()

                for (i in response.indices) {
                    val workoutListItem = WorkoutListItem()
                    val workoutList = response[i].workoutList
                    if (workoutList != null) {
                        val selectedWorkouts = ArrayList<Workout>()
                        for (j in workoutList.indices) {
                            if (workoutList[j].checked) {
                                selectedWorkouts.add(workoutList[j])
                            }
                        }
                        if (selectedWorkouts.size > 0) {
                            workoutListItem.type = response[i].type
                            workoutListItem.workoutList = selectedWorkouts
                        }
                    }
                    finalWorkoutItemList.add(workoutListItem)
                }
            }

            return finalWorkoutItemList
        }

    }
}