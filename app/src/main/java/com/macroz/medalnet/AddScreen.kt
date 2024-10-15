package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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


        binding.addButton.setOnClickListener {
            val medal = createMedalFromInput() ?: return@setOnClickListener
            m.dataViewModel.addMedal(medal, this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Callback function for when a medal is added
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

    // Callback function for when a medal fails to be added
    override fun onFailure(message: String, medal: Medal?) {
        if (message == "CONFLICT") {
            assert(medal != null)
            numberEditText.setText(medal?.number)
            nameEditText.setText(medal?.name)
            surnameEditText.setText(medal?.surname)
            rankEditText.setText(medal?.rank)
            unitEditText.setText(medal?.unit)
            yearEditText.setText(medal?.year?.toString() ?: "") // Convert year to string if it's an integer
            notesEditText.setText(medal?.notes)
            handleMedalConflict(medal!!)
            return
        }
        Toast.makeText(m, message, Toast.LENGTH_SHORT).show()
    }

    // Function to create a Medal object from input fields
    private fun createMedalFromInput(): Medal? {
        val number = numberEditText.text.toString()
        val name = nameEditText.text.toString()
        val surname = surnameEditText.text.toString()
        val rank = rankEditText.text.toString()
        val unit = unitEditText.text.toString()
        val yearString = yearEditText.text.toString()
        var year: Long? = null // Real year value used to create the Medal object
        val notes = notesEditText.text.toString()

        // Validate inputs
        if (number.isBlank()) {
            Toast.makeText(requireContext(), "Number field cannot be blank", Toast.LENGTH_SHORT)
                .show()
            return null
        }

        // Attempt to parse the year to a Long if user entered a year
        if (yearString.isNotBlank()) {
            try {
                year = yearString.toLong()  // Convert year to Long
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Please enter a valid year", Toast.LENGTH_SHORT).show()
                return null
            }
        }

        // Create and return a new Medal object
        return Medal(-1, number, name, surname, rank, unit, year, notes, -1)
    }

    // Function to show the modal dialog when a duplicate medal is found
    private fun handleMedalConflict(medal: Medal) {
        val builder = AlertDialog.Builder(requireContext());
        builder.setTitle("Medal Already Exists")
        builder.setMessage("The medal you're trying to add already exists. Would you like to edit the existing medal instead?")

        // Add "Go to Edit" button
        builder.setPositiveButton("Go to Edit Medal") { dialog, which ->
            // Navigate to the Edit Medal screen
            val bundle = Bundle()
            bundle.putParcelable("medal", medal)
            findNavController().navigate(R.id.EditScreen, bundle)
            dialog.dismiss()
        }

        // Add "Cancel" button
        builder.setNegativeButton("Cancel") { dialog, which ->
            // Dismiss the dialog and stay on the current screen
            dialog.dismiss()
        }

        // Show the modal
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}