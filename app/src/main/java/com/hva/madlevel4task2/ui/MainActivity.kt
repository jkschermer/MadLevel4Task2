package com.hva.madlevel4task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.hva.madlevel4task2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        // Listener for when the user presses the top left back button
        toolbar.setNavigationOnClickListener {
            // If there is a previous screen, go back to that screen
            if (navController.previousBackStackEntry != null)
                navController.popBackStack()
        }
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
            R.id.history ->  {
                navController.navigate(R.id.SecondFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // changes action bar for fragments
    // changes title, menu, and icons that are being shown on toolbar
    fun changeActionBar(titleToolbar: String, deleteItem: Int? = null, menu: Int, setArrow: Boolean) {
        // sets the title for the fragment
        toolbar.title = titleToolbar
        // removes item which is not needed for specific fragment
        if (deleteItem != null) {
            toolbar.menu.removeItem(deleteItem)
        }
        // sets the menu according to the fragment
        menuInflater.inflate(menu, toolbar.menu)
        // switch state of arrow, if true then arrow will be displayed
        supportActionBar?.setDisplayHomeAsUpEnabled(setArrow)
    }
}