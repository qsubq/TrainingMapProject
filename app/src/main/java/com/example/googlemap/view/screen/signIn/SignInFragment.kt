package com.example.googlemap.view.screen.signIn

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailFocusListener()
        passwordFocusListener()
        binding.btnLogin.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val validEmail = binding.textInputLayoutEmail.helperText == null
        val validPassword = binding.textInputLayoutPassword.helperText == null

        if (validEmail && validPassword) {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val isAlreadySeen: Boolean =
                sharedPref.getBoolean(getString(R.string.saved_onBoarding_bool), false)

            if (isAlreadySeen) {
                this.findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
            } else {
                this.findNavController().navigate(R.id.action_signInFragment_to_onBoardingFragment)
            }
        }
    }

    private fun emailFocusListener() {
        binding.etEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputLayoutEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.etEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }


    private fun passwordFocusListener() {
        binding.etPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.textInputLayoutPassword.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.etPassword.text.toString()
        if (passwordText.length < 8)
            return "Minimum 8 character password"
        if (!passwordText.matches(".*[A-Z].*".toRegex()))
            return "Must contain at-least 1 upper-case character"
        if (!passwordText.matches(".*[a-z].*".toRegex()))
            return "Must contain at-least 1 lower-case character"
        if (!passwordText.matches(".*[0-9].*".toRegex()))
            return "Must contain at-least 1 number character"
        return null
    }
}