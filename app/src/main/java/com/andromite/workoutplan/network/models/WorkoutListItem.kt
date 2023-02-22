package com.andromite.workoutplan.network.models


import com.google.gson.annotations.SerializedName

data class WorkoutListItem(
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("workout_list")
    var workoutList: List<Workout>? = listOf()
)