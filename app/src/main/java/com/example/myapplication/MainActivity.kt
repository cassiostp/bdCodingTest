package com.example.myapplication

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.myapplication.viewmodel.MainViewModel
import android.content.Intent
import android.net.Uri


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val projects = viewModel.projectsList
        projects.observe(this, Observer {
            val list = it.items ?: emptyArray()
            val menu = nav_view.menu
            val submenu = menu.addSubMenu("Github Repos")
            for (projectModel in list) {
                val item = submenu.add(projectModel.name)
                item.setOnMenuItemClickListener {
                    val url = projectModel.htmlUrl
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    true
                }
            }
        })
        viewModel.searchAndroidProjects()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)


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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_google -> {
                //navigate to google
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.googleFragment, true).build()
                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(R.id.googleFragment, null, navOptions)
            }
            R.id.nav_buttons -> {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.buttonsFragment, true).build()
                Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
                    .navigate(R.id.buttonsFragment, null, navOptions)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
