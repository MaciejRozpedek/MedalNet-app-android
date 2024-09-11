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
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun checkIfLoggedIn(): Boolean {
        token = prefs.getToken().orEmpty()
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAccountScreen() {
        val profileImage = findViewById<ImageView>(R.id.profile_image)
        val usernameText = findViewById<TextView>(R.id.username)
        val loginButton = findViewById<Button>(R.id.login_button)

        if (true) {
            // Show user details if logged in
            profileImage.setImageResource(R.drawable.user_avatar_filled) // Example avatar
            usernameText.text = "John Doe" // Replace with actual user info
            loginButton.visibility = View.GONE
        } else {
            // Show login option if not logged in
            profileImage.visibility = View.GONE
            usernameText.visibility = View.GONE
            loginButton.visibility = View.VISIBLE

            loginButton.setOnClickListener {
                // Handle login action
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}