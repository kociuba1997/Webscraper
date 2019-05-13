package com.newsscraper.ui

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.GetTagsReceiver
import com.newsscraper.ui.newslist.NewsListFragment
import com.newsscraper.utils.CircleTransform
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class NavigationActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    private lateinit var fbAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)
        fbAnalytics = FirebaseAnalytics.getInstance(this)
        navigationController = findNavController(R.id.navigationHostFragment)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    fun sendToAnalytics(title: String, bundle: Bundle) {
        fbAnalytics.logEvent(title, bundle)
    }

    fun setImage(imageView: ImageView, photoLink: String, rounded: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL(photoLink)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                if (bmp != null) {
                    if (rounded) {
                        imageView.setImageBitmap(CircleTransform().transform(bmp))
                    } else {
                        imageView.setImageBitmap(bmp)
                    }
                }
            }
        }
    }
}
