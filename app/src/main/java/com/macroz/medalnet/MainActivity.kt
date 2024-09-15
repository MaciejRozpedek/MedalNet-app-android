package com.macroz.medalnet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
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
        navController.addOnDestinationChangedListener { _, _, _ ->
            invalidateOptionsMenu()
        }
    }

    private fun checkIfLoggedIn(): Boolean {
        val token: String = dataViewModel.getToken()
        if (token.isEmpty()) return false
        if (JwtUtils.isTokenExpired(token)) return false
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        val accountItem = menu.findItem(R.id.action_profile_options)
        val settingsItem = menu.findItem(R.id.action_settings)
        // check in which fragment we are
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val currentDestination = navController.currentDestination?.id
        when (currentDestination) {
            R.id.LoginScreen,
            R.id.RegisterScreen,
            R.id.AccountScreen -> {
                accountItem.isVisible = false
                settingsItem.isVisible = false
            }
            else -> {
                accountItem.isVisible = true
                settingsItem.isVisible = true
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                dataViewModel.logout()
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.LoginScreen)
                true
            }

            R.id.action_profile_options -> {
                val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.enter_from_right)  // Enter AccountScreen from right
                    .setExitAnim(R.anim.exit_to_left)       // Exit current screen to left
                    .setPopEnterAnim(R.anim.enter_from_left) // Re-enter AccountScreen from left
                    .setPopExitAnim(R.anim.exit_to_right)   // Pop back (exit) to right
                    .build()
                findNavController(R.id.nav_host_fragment_content_main).navigate(
                    R.id.AccountScreen,
                    null,
                    navOptions
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}