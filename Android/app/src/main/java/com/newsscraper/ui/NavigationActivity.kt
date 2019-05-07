package com.newsscraper.ui

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.newsscraper.R
import com.newsscraper.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationController: NavController

    private lateinit var fbAnalytics: FirebaseAnalytics

    private var navIcon: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)


        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)



        fbAnalytics = FirebaseAnalytics.getInstance(this)

        navigationController = findNavController(R.id.navigationHostFragment)
//        navigationController
//        NavigationUI.setupActionBarWithNavController(this, navigationController)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navIcon = toolbar.navigationIcon
    }

    fun lockDrawerLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.navigationIcon = null
    }

    fun unlockDrawerLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.navigationIcon = navIcon
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    fun sendToAnalytics(title: String, bundle: Bundle) {
        fbAnalytics.logEvent(title, bundle)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun setImage(imageView: ImageView, photoLink: String, rounded: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL(photoLink)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                if(bmp != null) {
                    if(rounded) {
                        imageView.setImageBitmap(CircleTransform().transform(bmp))
                    } else {
                        imageView.setImageBitmap(bmp)
                    }
                }
            }
        }
    }
}
