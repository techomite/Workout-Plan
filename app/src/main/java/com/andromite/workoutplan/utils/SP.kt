package com.andromite.workoutplan.utils

import android.content.Context

class SP {

    companion object {

        fun save(context: Context, key: String, value: String) {
            val sharedPref =
                context.getSharedPreferences("asdfasdf", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(key, value)
                commit()
            }
        }

        fun get(context: Context, key: String): String {
            val sharedPref =
                context.getSharedPreferences("asdfasdf", Context.MODE_PRIVATE)
            val value = sharedPref.getString(key, "0")
            return value!!
        }

    }

}