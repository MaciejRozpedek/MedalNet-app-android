package com.macroz.medalnet

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.macroz.medalnet.adapters.CircleAdapter
import com.macroz.medalnet.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val footerTextView = binding.footerTextView
        val footerText = "Cookies Policy | Privacy Policy"
        val spannableString = SpannableString(footerText)

        val cookiesStart = footerText.indexOf("Cookies Policy")
        val cookiesEnd = cookiesStart + "Cookies Policy".length
        val privacyStart = footerText.indexOf("Privacy Policy")
        val privacyEnd = privacyStart + "Privacy Policy".length

        // Underline "Cookies Policy"
        spannableString.setSpan(UnderlineSpan(), cookiesStart, cookiesEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Underline "Privacy Policy"
        spannableString.setSpan(UnderlineSpan(), privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Add click event for "Cookies Policy"
        val cookiesClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
//                Toast.makeText(context, "Cookies Policy", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_FirstFragment_to_CookiesPolicyFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // Underline the text
                ds.color = ContextCompat.getColor(requireContext(), R.color.gray) // Set the text color to gray
            }
        }
        spannableString.setSpan(cookiesClickableSpan, cookiesStart, cookiesEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Add click event for "Privacy Policy"
        val privacyClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
//                Toast.makeText(context, "Privacy Policy", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_FirstFragment_to_PrivacyPolicyFragment)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // Underline the text
                ds.color = ContextCompat.getColor(requireContext(), R.color.gray) // Set the text color to gray
            }
        }
        spannableString.setSpan(privacyClickableSpan, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        footerTextView.text = spannableString
        footerTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onResume() {
        super.onResume()

        val recyclerView = binding.recyclerView
        val adapter = CircleAdapter(context!!, 5)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}