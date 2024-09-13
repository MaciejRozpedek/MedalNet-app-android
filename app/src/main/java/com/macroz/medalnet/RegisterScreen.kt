package com.macroz.medalnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.databinding.RegisterScreenBinding

class RegisterScreen : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: RegisterScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity

        m.dataViewModel.loginSuccess.observe(this) { isSuccess ->
            if (isSuccess == true) {
                Toast.makeText(m, "Registration successful! Welcome!", Toast.LENGTH_SHORT).show()
                m.dataViewModel.registerHandled()
                findNavController().navigate(R.id.action_RegisterScreen_to_FirstFragment)
            } else if (isSuccess == false) {
                Toast.makeText(m, "Login failed", Toast.LENGTH_SHORT).show()
                m.dataViewModel.registerHandled()
            }
        }
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
            val username: String = editTextUsername.text.toString().trimEnd()
            val email: String = editTextEmail.text.toString().trimEnd()
            val password: String = editTextPassword.text.toString().trimEnd()
            val confirmPassword: String = editTextConfirmPassword.text.toString().trimEnd()
            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(m, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!username.matches(Regex("[a-zA-Z0-9]+"))) {
                Toast.makeText(m, "Username can only contain letters and numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 7) {
                Toast.makeText(m, "Password must be at least 7 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(m, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(m, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            m.dataViewModel.register(email, username, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}