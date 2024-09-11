package com.macroz.medalnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.databinding.RegisterScreenBinding

class RegisterScreen : Fragment() {

    private var _binding: RegisterScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RegisterScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextUsername = binding.editTextUsername
        val editTextEmail = binding.editTextEmail
        val editTextPassword = binding.editTextPassword
        val editTextConfirmPassword = binding.editTextConfirmPassword
        val textViewSignUp = binding.textViewSignIn

        textViewSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterScreen_to_LoginScreen)
        }

        binding.registerButton.setOnClickListener {
            val username: String = editTextUsername.text.toString()
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()
            val confirmPassword: String = editTextConfirmPassword.text.toString()
            //TODO(register)

            findNavController().navigate(R.id.action_RegisterScreen_to_LoginScreen)
            prefs.saveEmail(email)
            prefs.savePassword(password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}