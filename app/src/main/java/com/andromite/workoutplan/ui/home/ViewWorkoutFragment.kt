package com.andromite.workoutplan.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andromite.workoutplan.databinding.FragmentViewWorkoutBinding
import com.andromite.workoutplan.ui.workout.EditWorkoutActivity
import com.andromite.workoutplan.utils.FireStoreUtils
import com.andromite.workoutplan.utils.Utils
import com.andromite.workoutplan.utils.WorkoutHelper
import java.util.*


class ViewWorkoutFragment : Fragment() {
    private lateinit var binding : FragmentViewWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewWorkoutBinding.inflate(inflater, container, false)

        initClickListener()
        getWorkoutList()
        return binding.root
    }

    private fun initClickListener() {
        binding.createNewButton.setOnClickListener {
            val intent = Intent(requireContext(), EditWorkoutActivity::class.java)
            intent.putExtra("type","new")
            intent.putExtra("date", Utils().getFormattedDate(Date()))
            startActivity(intent)
        }

        binding.selectExistingButton.setOnClickListener {
            val intent = Intent(requireContext(), EditWorkoutActivity::class.java)
            intent.putExtra("type","update")
            intent.putExtra("date", Utils().getFormattedDate(Date()))
            startActivity(intent)
//            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getWorkoutList() {
        binding.progressBar.visibility = View.VISIBLE
        FireStoreUtils().readWorkoutList(requireContext(), Utils().getFormattedDate(Date())){
            val selectedWorkoutList = WorkoutHelper.getSelectedWorkoutList(it)


            if (selectedWorkoutList.isNotEmpty()) {

                showEmptyLayout()

//                for (i in selectedWorkoutList.indices) {
//                    Log.e("asdfasdf", "type: ${selectedWorkoutList[i].type}")
//                    if (selectedWorkoutList[i].workoutList?.isNotEmpty() == true) {
//                        for (j in selectedWorkoutList[i].workoutList?.indices!!) {
//                            Log.e(
//                                "asdfasdf",
//                                "workout name: ${selectedWorkoutList[i].workoutList?.get(j)?.name}"
//                            )
//                        }
//                    }
//                }
            } else {
                showEmptyLayout()
            }
        }
    }

    private fun showEmptyLayout() {
        binding.progressBar.visibility = View.GONE
        binding.emptyLayout.visibility = View.VISIBLE
    }
}