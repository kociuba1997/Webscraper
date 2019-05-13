package com.newsscraper.ui.login

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.newsscraper.R
import com.newsscraper.services.ServiceManager
import com.newsscraper.services.apireceivers.LoginReceiver
import com.newsscraper.transportobjects.UserDTO
import com.newsscraper.ui.NavigationActivity
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), LoginReceiver {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            ServiceManager.login(this, UserDTO(usernameEditText.text.toString(), passwordEditText.text.toString()))
        }

        registerTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        (activity as NavigationActivity).closeOptionsMenu()
        setHasOptionsMenu(false)
        usernameEditText.setText("hamulec")
        passwordEditText.setText("hamulec")
    }

    override fun onLoginSuccess(token: String) {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_newsListFragment)
    }

    override fun onLoginError() {}

}
