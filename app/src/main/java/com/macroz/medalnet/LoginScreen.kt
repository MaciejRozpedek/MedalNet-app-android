package com.macroz.medalnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.databinding.LoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: LoginScreenBinding? = null

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
                Toast.makeText(m, "Login successful", Toast.LENGTH_SHORT).show()
                m.dataViewModel.loginHandled()
                findNavController().navigate(R.id.action_LoginScreen_to_FirstFragment)
            } else if (isSuccess == false) {
                Toast.makeText(m, "Login failed", Toast.LENGTH_SHORT).show()
                m.dataViewModel.loginHandled()
            }
        }
        _binding = LoginScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextEmail = binding.editTextEmail
        val editTextPassword = binding.editTextPassword
        val textViewSignUp = binding.textViewSignUp

        editTextEmail.setText(m.dataViewModel.getEmail())
        editTextPassword.setText(m.dataViewModel.getPassword())

        textViewSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_LoginScreen_to_RegisterScreen)
        }

        binding.loginButton.setOnClickListener {
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()
            m.dataViewModel.login("email", email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}