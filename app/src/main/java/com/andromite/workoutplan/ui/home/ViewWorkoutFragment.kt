package com.andromite.workoutplan.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andromite.workoutplan.databinding.FragmentViewWorkoutBinding
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.ui.workout.EditWorkoutActivity
import com.andromite.workoutplan.utils.FireStoreUtils
import com.andromite.workoutplan.utils.Utils
import com.andromite.workoutplan.utils.WorkoutHelper
import java.util.*
import kotlin.collections.ArrayList


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
            resultLauncher.launch(intent)
        }

        binding.selectExistingButton.setOnClickListener {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.updateButton.setOnClickListener {
            val intent = Intent(requireContext(), EditWorkoutActivity::class.java)
            intent.putExtra("type","update")
            intent.putExtra("date", Utils().getFormattedDate(Date()))
            resultLauncher.launch(intent)
        }

        binding.deleteButton.setOnClickListener {
            hideRV()
            hideEmptyLayout()
            deleteWorkoutList()
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            hideRV()
            hideEmptyLayout()
            getWorkoutList()
        }
    }

    private fun getWorkoutList() {
        binding.progressBar.visibility = View.VISIBLE
        FireStoreUtils().readWorkoutList(requireContext(), Utils().getFormattedDate(Date())){
            val selectedWorkoutList = WorkoutHelper.getSelectedWorkoutList(it)

            if (selectedWorkoutList.isNotEmpty()) {
                showRV()
                val finalList = ArrayList<Workout>()
                for (item in selectedWorkoutList){
                    finalList.addAll(item.workoutList)
                }
                initRV(finalList)
            } else {
                showEmptyLayout()
            }
        }
    }

    private fun initRV(selectedWorkoutList: ArrayList<Workout>) {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = ViewWorkoutListAdapter(selectedWorkoutList)
    }

    private fun deleteWorkoutList() {
        FireStoreUtils().deleteWorkoutList(requireContext(), Utils().getFormattedDate(Date())) {
            showEmptyLayout()
        }
    }

    private fun showEmptyLayout() {
        binding.progressBar.visibility = View.GONE
        binding.emptyLayout.visibility = View.VISIBLE
    }

    private fun hideEmptyLayout() {
        binding.progressBar.visibility = View.VISIBLE
        binding.emptyLayout.visibility = View.GONE
    }

    private fun showRV() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    private fun hideRV() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }
}