package com.macroz.medalnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.databinding.LoginScreenBinding

class LoginScreen : Fragment() {

    private var _binding: LoginScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LoginScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextEmail = binding.editTextEmail
        val editTextPassword = binding.editTextPassword
        val textViewSignUp = binding.textViewSignUp

        editTextEmail.setText(prefs.getEmail())
        editTextPassword.setText(prefs.getPassword())

        textViewSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginScreen_to_RegisterScreen)
        }

        binding.loginButton.setOnClickListener {
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()
            //TODO(login)

            findNavController().navigate(R.id.action_LoginScreen_to_FirstFragment)
            prefs.saveEmail(email)
            prefs.savePassword(password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}