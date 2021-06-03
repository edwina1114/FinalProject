package com.example.test

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test.database.SceneDatabase
import com.example.test.databinding.ActivityMainBinding
import com.example.test.viewModel.MyViewModel
import com.example.test.viewModel.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //////Bottom Navigation//////
        setupViews()
        //////Bottom Navigation//////

        //setup navigation controller with the up button
        navController = this.findNavController(R.id.navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        //retrieve the database dao
        val application = requireNotNull(this).application
        val dataSource = SceneDatabase.getInstance(application).sceneDatabaseDao

        //get the shared viewModel associated with the activity
        viewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)

        //check whether the database is created or not
        //database will be initialized once in this project
        //write a mark to a sharedpreference file
        sp = getPreferences(Context.MODE_PRIVATE)
        val databaseState = sp.getBoolean("Created", false)
        if (!databaseState) {
            viewModel.initDB()
            val editor = sp.edit()
            editor.putBoolean("Created", true)
            editor.apply()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun setupViews()
    {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.navController)

        // Setting Navigation Controller with the BottomNavigationView
        binding.bottomNavigation.setupWithNavController(navController)

        // Setting Up ActionBar with Navigation Controller
        // Pass the IDs of top-level destinations in AppBarConfiguration
        var appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.listFragment,
                R.id.aboutFragment,
                R.id.weatherFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}