package com.andromite.workoutplan.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager


class Utils {

    companion object{
        fun flog(value: Any) {
            if (Constants.ENABLE_LOGS)
            Log.i("BR", ":-: $value :-:")
        }

        fun floge(value: Any) {
            if (Constants.ENABLE_LOGS)
                Log.e("asdfasdf", ":-: $value :-:")
        }

        fun isUserLoggedIn(context: Context): Boolean {
            return SP.get(context, Enums.UserId.name) != "0"
        }

        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view: View? = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }
}