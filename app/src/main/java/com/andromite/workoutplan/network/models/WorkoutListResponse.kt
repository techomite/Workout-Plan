package com.andromite.workoutplan.network.models


import com.google.gson.annotations.SerializedName

data class WorkoutListResponse(
    @SerializedName("list")
    val list: List<WorkoutListItem> = listOf()
)