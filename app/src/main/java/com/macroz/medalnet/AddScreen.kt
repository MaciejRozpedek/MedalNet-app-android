package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.databinding.AddScreenBinding

class AddScreen : Fragment() {

    //    private lateinit var m: MainActivity
    private var _binding: AddScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AddScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberEditText: EditText = binding.addScreenMedalNumber
        val nameEditText: EditText = binding.addScreenMedalName
        val surnameEditText: EditText = binding.addScreenMedalSurname
        val rankEditText: EditText = binding.addScreenMedalRank
        val unitEditText: EditText = binding.addScreenMedalUnit
        val yearEditText: EditText = binding.addScreenMedalYear
        val notesEditText: EditText = binding.addScreenMedalNotes


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
//            TODO("send to server the new medal")
            numberEditText.text.clear()
            nameEditText.text.clear()
            surnameEditText.text.clear()
            rankEditText.text.clear()
            unitEditText.text.clear()
            yearEditText.text.clear()
            notesEditText.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}