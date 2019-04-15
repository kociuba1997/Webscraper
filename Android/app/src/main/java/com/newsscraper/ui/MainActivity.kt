package com.newsscraper.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.analytics.FirebaseAnalytics
import com.newsscraper.R
import com.newsscraper.transportobjects.NewsDTO
import com.newsscraper.utils.CircleTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

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
