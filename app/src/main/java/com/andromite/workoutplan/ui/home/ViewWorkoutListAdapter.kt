package com.andromite.workoutplan.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andromite.workoutplan.R
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem

class ViewWorkoutListAdapter(var selectedWorkoutList: ArrayList<Workout>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_item_layout, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameTextView.text = selectedWorkoutList[position].name
        holder.typeTextView.text = selectedWorkoutList[position].type
        holder.setsTextView.text = selectedWorkoutList[position].sets.toString()
    }

    override fun getItemCount(): Int {
        return  selectedWorkoutList.size
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var nameTextView : TextView = view.findViewById(R.id.nameTextView)
    var typeTextView : TextView = view.findViewById(R.id.typeTextView)
    var setsTextView : TextView = view.findViewById(R.id.setsTextView)
}
