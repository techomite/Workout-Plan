package com.andromite.workoutplan.ui.workout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andromite.workoutplan.R
import com.andromite.workoutplan.network.models.Workout
import com.andromite.workoutplan.network.models.WorkoutListItem
import com.andromite.workoutplan.utils.Utils

class WorkoutListAdapter(var listener: WorkoutItemClickListener, var type: String?) :
    RecyclerView.Adapter<WorkoutListAdapter.ViewHolder>() {

    val list: MutableList<Workout> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_workout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener, type)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var checkBoxView = view.findViewById<CheckBox>(R.id.checkBoxView)
        var nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        var setsTextView = view.findViewById<TextView>(R.id.setsTextView)

        fun bind(workout: Workout, listener: WorkoutItemClickListener, type: String?) {
            checkBoxView.isChecked = workout.checked
            nameTextView.text = workout.name
            setsTextView.text = workout.sets.toString()

            checkBoxView.setOnCheckedChangeListener { compoundButton, b ->
                listener.workoutChecked(type, workout, b)
            }
        }
    }

    fun addAll(childItems: List<WorkoutListItem>?) {
        if (childItems != null) {
            for (tor in childItems) {
                if (tor.type.equals(type)) {
                    for (item in tor.workoutList!!) {
                        list.add(item)
                    }
                }
                notifyItemInserted(list.size - 1)
            }
        }
    }

    interface WorkoutItemClickListener {
        fun workoutChecked(type: String?, selectedWorkout: Workout, checked : Boolean)
    }
}