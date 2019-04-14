package com.newsscraper.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.newsscraper.R

class MainActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    private lateinit var fbAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbAnalytics = FirebaseAnalytics.getInstance(this)

        navigationController = findNavController(R.id.navigationHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    fun sendToAnalytics(title: String, bundle: Bundle) {
        fbAnalytics.logEvent(title, bundle)
    }
}
