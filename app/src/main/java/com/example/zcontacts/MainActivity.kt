package com.example.zcontacts

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.zcontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ZContacts)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navMenu, navController)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color: Int = resources.getColor(R.color.toolBarColor)
            window?.navigationBarColor = color
        }

        // Log.i("hello", "onCreate:$databaseDao ")

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}