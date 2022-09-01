package com.andromite.workoutplan.network.models


import com.google.gson.annotations.SerializedName

data class TemplateResponseItem(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("workout_list")
    val workoutList: List<Workout?>? = null
)