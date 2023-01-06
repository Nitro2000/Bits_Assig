package com.example.bitsdashboardassign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bitsdashboardassign.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        val navController = navHostFragment.navController

//        val navController = findNavController(R.id.navController)
        binding.bottomNavBar.setupWithNavController(navController)
    }

    fun bottonNavBarVisibility(visibility: Int) {
        binding.bottomNavBar.visibility = visibility
    }
}