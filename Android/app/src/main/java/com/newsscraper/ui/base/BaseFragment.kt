package com.newsscraper.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.newsscraper.services.ServiceManager
import com.newsscraper.ui.NavigationActivity

open class BaseFragment : Fragment() {

    lateinit var parentActivity: NavigationActivity
    lateinit var serviceManager: ServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = activity as NavigationActivity
        serviceManager = parentActivity.serviceManager
    }

    fun startProgressDialog(title: String = "Loading...") {
        parentActivity.startProgressDialog(title)
    }

    fun stopProgressDialog() {
        parentActivity.stopProgressDialog()
    }
}