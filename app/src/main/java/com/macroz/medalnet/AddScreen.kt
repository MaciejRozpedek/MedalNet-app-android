package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.databinding.AddScreenBinding
import com.macroz.medalnet.repository.DataRepository

class AddScreen : Fragment(), DataRepository.AddMedalCallback {

        private lateinit var m: MainActivity
    private var _binding: AddScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var numberEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var rankEditText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var notesEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity
        _binding = AddScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        numberEditText = binding.addScreenMedalNumber
        nameEditText = binding.addScreenMedalName
        surnameEditText = binding.addScreenMedalSurname
        rankEditText = binding.addScreenMedalRank
        unitEditText = binding.addScreenMedalUnit
        yearEditText = binding.addScreenMedalYear
        notesEditText = binding.addScreenMedalNotes


        binding.addButton.setOnClickListener{
            val medal = Medal(
                id = -1,
                number = numberEditText.text.toString(),
                name = nameEditText.text.toString(),
                surname = surnameEditText.text.toString(),
                rank = rankEditText.text.toString(),
                unit = unitEditText.text.toString(),
                year = yearEditText.text.toString().toLongOrNull(),
                notes = notesEditText.text.toString(),
                userId = -1
            )

            m.dataViewModel.addMedal(medal, this)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun onSuccess() {
        numberEditText.text.clear()
        nameEditText.text.clear()
        surnameEditText.text.clear()
        rankEditText.text.clear()
        unitEditText.text.clear()
        yearEditText.text.clear()
        notesEditText.text.clear()

        Toast.makeText(m, "Medal successfully added!", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(m, message, Toast.LENGTH_SHORT).show()
    }
}