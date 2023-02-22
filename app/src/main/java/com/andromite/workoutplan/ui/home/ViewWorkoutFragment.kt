package com.andromite.workoutplan.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andromite.workoutplan.R
import com.andromite.workoutplan.utils.FireStoreUtils
import com.andromite.workoutplan.utils.Utils
import com.andromite.workoutplan.utils.WorkoutHelper
import java.util.*


class ViewWorkoutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_workout, container, false)

        getWorkoutList()
        return view
    }

    private fun getWorkoutList() {
        FireStoreUtils().readWorkoutList(requireContext(), Utils().getFormattedDate(Date())){
            val selectedWorkoutList = WorkoutHelper.getSelectedWorkoutList(it)

            for (i in selectedWorkoutList.indices) {
                Log.e("asdfasdf", "type: ${selectedWorkoutList[i].type}")
                if (selectedWorkoutList[i].workoutList?.isNotEmpty() == true) {
                    for (j in selectedWorkoutList[i].workoutList?.indices!!) {
                        Log.e(
                            "asdfasdf",
                            "workout name: ${selectedWorkoutList[i].workoutList?.get(j)?.name}"
                        )
                    }
                }
            }

        }
    }
}