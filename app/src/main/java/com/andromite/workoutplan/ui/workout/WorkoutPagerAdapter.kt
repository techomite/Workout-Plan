package com.andromite.workoutplan.ui.workout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class WorkoutPagerAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm) {

    private var fragmentList: MutableList<Fragment> = ArrayList()
    private var fragmentTitle: MutableList<String> = ArrayList()

    fun add(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
       return fragmentList[position]
    }

}