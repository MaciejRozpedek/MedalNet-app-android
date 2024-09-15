package com.macroz.medalnet

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.data.User
import com.macroz.medalnet.databinding.AccountScreenBinding


class AccountScreen : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: AccountScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var user: User? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity

        _binding = AccountScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageViewPfp: ImageView = binding.imageViewPfp
        val textViewUsername: TextView = binding.textViewUsername

        m.dataViewModel.getUserProfile().observe(viewLifecycleOwner) { user ->
            this.user = user
            textViewUsername.text = user.username

            if (!user.base64profilePicture.isNullOrEmpty()) {

                try {
                    val decodedString: ByteArray =
                        Base64.decode(user.base64profilePicture, Base64.DEFAULT)
                    val decodedBitmap =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    imageViewPfp.setImageBitmap(decodedBitmap)
                } catch (_: Exception) {
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            m.dataViewModel.logout()
            findNavController().navigate(R.id.action_AccountScreen_to_LoginScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}