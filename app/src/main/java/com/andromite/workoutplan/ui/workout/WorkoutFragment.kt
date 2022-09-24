package com.andromite.workoutplan.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.andromite.workoutplan.databinding.FragmentWorkoutBinding
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutFragment(var type: String?, var viewModel: WorkoutSharedViewModel) : Fragment(), WorkoutListAdapter.WorkoutItemClickListener {


    lateinit var binding : FragmentWorkoutBinding
    var workoutList = ArrayList<WorkoutListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutBinding.inflate(layoutInflater)


        registerDataRequest()


        return binding.root
    }

    private fun registerDataRequest() {
        viewModel.workoutListLiveData.observe(requireActivity()){
            it?.get(0)?.workoutList?.get(0)?.checked = true
            displaySelectedWorkoutList(it)
        }
    }

    private fun displaySelectedWorkoutList(workoutListResponse: List<WorkoutListItem>?) {
        workoutList = workoutListResponse as ArrayList<WorkoutListItem>

        val adapter = WorkoutListAdapter(this, type)
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listRV.layoutManager = manager
        binding.listRV.adapter = adapter

        for (item in workoutListResponse){
            if (item.type == type){
                Utils.floge("item $item")
                adapter.addAll(workoutListResponse)
            }
        }
    }

    override fun workoutChecked(type: String?, selectedWorkout: Workout, checked : Boolean) {
        viewModel.updateWorkoutItem(type, selectedWorkout, checked)
    }


}