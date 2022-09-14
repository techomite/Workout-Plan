package com.andromite.workoutplan.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andromite.workoutplan.databinding.FragmentWorkoutBinding
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.network.models.WorkoutListResponse
import com.andromite.workoutplan.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutFragment(var type : String?) : Fragment() {


    lateinit var binding : FragmentWorkoutBinding
    lateinit var viewModel: WorkoutSharedViewModel
    var workoutList = ArrayList<WorkoutListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[WorkoutSharedViewModel::class.java]

        registerDataRequest()


        return binding.root
    }

    private fun registerDataRequest() {
        viewModel.workoutList.observe(requireActivity()){
            it.list?.get(0)?.workoutList?.get(0)?.checked = true
            getListFromType(it)
        }
        viewModel.getWorkoutList()
    }

    fun getListFromType(workoutListResponse: WorkoutListResponse) {
        workoutList = workoutListResponse.list as ArrayList<WorkoutListItem>

        var adapter = WorkoutListAdapter()
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listRV.layoutManager = manager
        binding.listRV.adapter = adapter

        for (item in workoutListResponse.list){
            if (item.type == type){
                Utils.floge("$item")
                adapter.addAll(item.workoutList as MutableList<Workout>)
            }

        }
    }


}