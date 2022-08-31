package com.andromite.workoutplan.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andromite.workoutplan.R
import com.andromite.workoutplan.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.workout -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.calendar -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }


    }
}