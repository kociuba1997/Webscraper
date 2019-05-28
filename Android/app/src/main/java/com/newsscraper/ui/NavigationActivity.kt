package com.newsscraper.ui

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.transportobjects.TagsDTO
import com.newsscraper.utils.CircleTransform
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class NavigationActivity : AppCompatActivity() {

    val serviceManager = ServiceManager
    private var progress: ProgressDialog? = null
    private lateinit var navigationController: NavController
    private lateinit var fbAnalytics: FirebaseAnalytics

    var hiddenTags: MutableList<String?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)
        fbAnalytics = FirebaseAnalytics.getInstance(this)
        navigationController = findNavController(R.id.navigationHostFragment)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fbAnalytics.setAnalyticsCollectionEnabled(true)
    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    fun sendToAnalytics(title: String, bundle: Bundle) {
        fbAnalytics.logEvent(title, bundle)
    }

    fun setImage(imageView: ImageView, photoLink: String, rounded: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            if (!photoLink.contains(".pl/185.")) {
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
        stopProgressDialog()
    }

    fun startProgressDialog(title: String) {
        if ((progress == null || progress?.isShowing == false) && !isFinishing) {
            progress = ProgressDialog.show(this, title, "", true)
            Handler().postDelayed({
                if (isFinishing && progress?.isShowing == true) {
                    stopProgressDialog()
                }
            }, 10000)
        }
    }

    fun stopProgressDialog() {
        if (!isFinishing && progress?.isShowing == true) {
            progress?.cancel()
        }
    }
}
