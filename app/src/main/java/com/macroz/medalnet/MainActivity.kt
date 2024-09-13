package com.macroz.medalnet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.macroz.medalnet.databinding.ActivityMainBinding
import com.macroz.medalnet.utils.JwtUtils
import com.macroz.medalnet.viewModel.DataViewModel
import com.macroz.medalnet.viewModel.DataViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val dataViewModel: DataViewModel by viewModels {
        DataViewModelFactory((application as MedalNetApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.nav_graph)

        val isLoggedIn: Boolean = checkIfLoggedIn()

        if (isLoggedIn) navGraph.setStartDestination(R.id.FirstFragment)
        else navGraph.setStartDestination(R.id.LoginScreen)

        navController.graph = navGraph
        val topLevelDestinations = setOf(R.id.FirstFragment, R.id.LoginScreen, R.id.RegisterScreen)
        appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun checkIfLoggedIn(): Boolean {
        var token: String = dataViewModel.getToken()
        if (token.isEmpty()) return false
        if (JwtUtils.isTokenExpired(token)) return false
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
        }
        return when (item.itemId) {
            R.id.action_settings -> {
                dataViewModel.logout()
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_FirstFragment_to_LoginScreen)
                true
            }

//            R.id.action_profile_options -> {
//                navController.navigate(R.id.AccountScreen)
//                true
//            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}