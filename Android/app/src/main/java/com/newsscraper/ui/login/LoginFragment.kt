package com.newsscraper.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.newsscraper.R
import com.newsscraper.services.apireceivers.LoginReceiver
import com.newsscraper.transportobjects.UserDTO
import com.newsscraper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment(), LoginReceiver {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            startProgressDialog()
            serviceManager.login(this, UserDTO(usernameEditText.text.toString(), passwordEditText.text.toString()))
        }

        registerTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        parentActivity.closeOptionsMenu()
        setHasOptionsMenu(false)
        usernameEditText.setText("hamulec")
        passwordEditText.setText("hamulec")
    }

    override fun onLoginSuccess(token: String) {
        stopProgressDialog()
        serviceManager.setToken(token)
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_newsListFragment)
    }

    override fun onLoginError() {
        stopProgressDialog()
    }

}
