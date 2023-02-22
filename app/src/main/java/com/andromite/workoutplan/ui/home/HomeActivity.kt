package com.andromite.workoutplan.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andromite.workoutplan.R
import com.andromite.workoutplan.databinding.ActivityMainBinding
import com.andromite.workoutplan.ui.workout.EditWorkoutActivity
import java.util.*

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

//        startActivity(Intent(this, EditWorkoutActivity::class.java))
        initViewPager()
        initBottomNav()

    }

    fun initBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.workout -> {
                    // Respond to navigation item 1 click
                    binding.pager.setCurrentItem(0, true)
                    true
                }
                R.id.calendar -> {
                    // Respond to navigation item 2 click
                    binding.pager.setCurrentItem(1, true)
                    true
                }
                else -> false
            }
        }
    }

    fun initViewPager(){
        binding.pager.adapter = ViewPagerAdapter(this)
        //asdfasdf
    }

}