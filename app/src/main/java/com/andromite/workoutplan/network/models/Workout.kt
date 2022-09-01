package com.andromite.workoutplan.network.models


import com.google.gson.annotations.SerializedName

data class Workout(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("sets")
    val sets: Int? = null
)