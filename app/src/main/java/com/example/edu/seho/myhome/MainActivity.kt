package com.example.edu.seho.myhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.edu.seho.myhome.fragments.foodPictures.FoodPicturesFragmentDirections
import com.example.edu.seho.myhome.fragments.startmenu.StartMenuFragmentDirections
import com.google.android.material.navigation.NavigationView

/** @author Sebastian Holm
 *  The main activity which displays all fragments in a fragment container view.
 *  It also handles all navigation to fragments from the navigation drawer.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var drawer : DrawerLayout

    private lateinit var toolbar : Toolbar

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        // Sets up the toolbar and navigation drawer with the navController
        setupWithNavController(toolbar, navController, drawer)

        // Listener for all items in the navigation drawer.
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_list -> {
                    navController.navigate(R.id.action_startMenuFragment_to_listFragment)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_add -> {
                    navController.navigate(R.id.action_startMenuFragment_to_addFragment)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_pictures -> {
                    navController.navigate(R.id.action_startMenuFragment_to_foodPicturesFragment)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_kyl -> {
                    val action = StartMenuFragmentDirections.actionStartMenuFragmentToPictureTakerFragment("kyl")
                    navController.navigate(action)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_frys -> {
                    val action = StartMenuFragmentDirections.actionStartMenuFragmentToPictureTakerFragment("frys")
                    navController.navigate(action)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_skafferi -> {
                    val action = StartMenuFragmentDirections.actionStartMenuFragmentToPictureTakerFragment("skafferi")
                    navController.navigate(action)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_krydd -> {
                    val action = StartMenuFragmentDirections.actionStartMenuFragmentToPictureTakerFragment("kryddor")
                    navController.navigate(action)
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.shopping_list -> {
                    navController.navigate(R.id.action_startMenuFragment_to_shoppinglist)
                    if (drawer.isDrawerOpen(GravityCompat.START)){
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                else -> {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }
    }

    // Closes navigation drawer when back is pressed
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // Navigates back to the previous fragment
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
