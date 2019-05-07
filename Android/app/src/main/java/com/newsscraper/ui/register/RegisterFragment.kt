package com.newsscraper.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.RegisterReceiver
import com.newsscraper.transportobjects.UserDTO
import com.newsscraper.ui.NavigationActivity
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(), RegisterReceiver {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationActivity).lockDrawerLayout()
        registerButton.setOnClickListener {
            ServiceManager.register(this, UserDTO(usernameEditText.text.toString(), passwordEditText.text.toString()))
        }
    }

    override fun onRegisterSuccess() {
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun onRegisterError() {}

}
