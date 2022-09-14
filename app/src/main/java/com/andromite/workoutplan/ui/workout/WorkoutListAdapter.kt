package com.andromite.workoutplan.ui.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andromite.workoutplan.R
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.utils.Utils

class WorkoutListAdapter : RecyclerView.Adapter<WorkoutListAdapter.ViewHolder>() {

    val list : MutableList<Workout> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_workout_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        Utils.floge("item count: ${list.size}")
        return list.size
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {

        var checkBoxView = view.findViewById<CheckBox>(R.id.checkBoxView)
        var nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        var setsTextView = view.findViewById<TextView>(R.id.setsTextView)

        fun bind(workout: Workout) {
            checkBoxView.isChecked = workout.checked
            nameTextView.text = workout.name
            setsTextView.text = workout.sets.toString()
        }
    }

    fun addAll(childItems: MutableList<Workout>) {
        for(tor in childItems){
            list.add(tor)
            notifyItemInserted(list.size - 1)
        }
    }
}