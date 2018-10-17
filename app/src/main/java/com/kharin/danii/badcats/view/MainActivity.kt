package com.kharin.danii.badcats.view

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.kharin.danii.badcats.R
import com.kharin.danii.badcats.di.DaggerMainActivityComponent
import com.kharin.danii.badcats.di.MainModule
import com.kharin.danii.badcats.di.NetworkModule
import com.kharin.danii.badcats.di.RepositoryModule
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    val appComponent = DaggerMainActivityComponent.builder()
        .mainModule(MainModule(this))
        .networkModule(NetworkModule())
        .repositoryModule(RepositoryModule())
        .build()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.cats_title)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        NavigationUI.setupWithNavController(nav_view,nav_host_fragment.findNavController())
        nav_host_fragment.findNavController().addOnNavigatedListener { controller, destination ->{}
            val title = destination.label
            if (!TextUtils.isEmpty(title)) {
                toolbar.title = title
            }
        }
    }

    override fun onSupportNavigateUp()
            = nav_host_fragment.findNavController().navigateUp()



}
