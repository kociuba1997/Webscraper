package com.newsscraper.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.newsscraper.R
import com.newsscraper.services.apireceivers.RegisterReceiver
import com.newsscraper.transportobjects.UserDTO
import com.newsscraper.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseFragment(), RegisterReceiver {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton.setOnClickListener {
            onClickRegisterButton()
        }
    }

    private fun onClickRegisterButton() {
        if (passwordEditText.text.toString() == repeatPasswordEditText.text.toString() &&
            usernameEditText.text.isNotEmpty() &&
            passwordEditText.text.isNotEmpty()
        ) {
            startProgressDialog()
            serviceManager.register(this, UserDTO(usernameEditText.text.toString(), passwordEditText.text.toString()))
        } else {
            repeatPasswordEditText.error = "hasła nie pasują do siebie"
        }
    }

    override fun onRegisterSuccess() {
        stopProgressDialog()
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
    }

    override fun onRegisterError() {
        stopProgressDialog()
    }

}
