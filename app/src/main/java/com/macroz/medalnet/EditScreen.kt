package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.databinding.EditScreenBinding
import com.macroz.medalnet.repository.DataRepository

class EditScreen : Fragment(), DataRepository.AddMedalCallback {

        private lateinit var m: MainActivity
    private var _binding: EditScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity
        _binding = EditScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medalNumberTextView = binding.medalNumberTextView
        val medalNameEditText = binding.medalNameEditText
        val medalSurnameEditText = binding.medalSurnameEditText
        val medalRankEditText = binding.medalRankEditText
        val medalUnitEditText = binding.medalUnitEditText
        val medalYearEditText = binding.medalYearEditText
        val medalNotesEditText = binding.medalNotesEditText
        val saveMedalButton = binding.saveMedalButton
        val cancelButton = binding.cancelButton

        if (arguments != null) {
            val medal: Medal? = arguments?.getParcelable("medal")
            if (medal != null){
                medalNumberTextView.text = medal.number
                medalNameEditText.setText(medal.name)
                medalSurnameEditText.setText(medal.surname)
                medalRankEditText.setText(medal.rank)
                medalUnitEditText.setText(medal.unit)
                medalYearEditText.setText(medal.year.toString())
                medalNotesEditText.setText(medal.notes)
            }
        }
        saveMedalButton.setOnClickListener {
            val medal = Medal(
                id = -1L,
                number = medalNumberTextView.text.toString(),
                name = medalNameEditText.text.toString(),
                surname = medalSurnameEditText.text.toString(),
                rank = medalRankEditText.text.toString(),
                unit = medalUnitEditText.text.toString(),
                year = medalYearEditText.text.toString().toLong(),
                notes = medalNotesEditText.text.toString(),
                userId = -1L
            )
            m.dataViewModel.updateMedal(medal, this)
        }

        cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onSuccess() {
        Toast.makeText(m, "Medal successfully saved!", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(m, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}