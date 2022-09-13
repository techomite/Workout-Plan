package com.andromite.workoutplan.network.models


import com.google.gson.annotations.SerializedName

data class WorkoutListItem(
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("workout_list")
    val workoutList: List<Workout>? = listOf()
)