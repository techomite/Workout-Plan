package com.andromite.workoutplan.ui.home

import android.app.Activity
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fm : FragmentActivity): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return ViewWorkoutFragment()
            1 -> return EditWorkoutFragment()
        }
        return ViewWorkoutFragment()
    }




}