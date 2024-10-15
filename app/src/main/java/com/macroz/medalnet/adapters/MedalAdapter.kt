package com.macroz.medalnet.adapters

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.macroz.medalnet.R
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.adapters.MedalAdapter.MedalViewHolder

class MedalAdapter : ListAdapter<Medal, MedalViewHolder>(MEDAL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedalViewHolder {
        return MedalViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MedalViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current.id == (-1).toLong(),
            current.number,
            current.name,
            current.surname,
            current.rank,
            current.unit,
            current.year,
            current.notes
        )

    }

    class MedalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val medalNumber: TextView = itemView.findViewById(R.id.medalNumber)
        private val medalName: TextView = itemView.findViewById(R.id.medalName)
        private val medalSurname: TextView = itemView.findViewById(R.id.medalSurname)
        private val medalRank: TextView = itemView.findViewById(R.id.medalRank)
        private val medalUnit: TextView = itemView.findViewById(R.id.medalUnit)
        private val medalYear: TextView = itemView.findViewById(R.id.medalYear)
        private val medalNotes: TextView = itemView.findViewById(R.id.medalNotes)
        private val editButton: Button = itemView.findViewById(R.id.editButton)

        fun bind(
            display: Boolean,
            number: String?,
            name: String?,
            surname: String?,
            rank: String?,
            unit: String?,
            year: Long?,
            notes: String?
        ) {
            if (display) {
                medalNumber.text = "NUMER"
                medalNumber.setTypeface(medalNumber.typeface, Typeface.BOLD)
                medalName.text = "IMIĘ"
                medalName.setTypeface(medalName.typeface, Typeface.BOLD)
                medalSurname.text = "NAZWISKO"
                medalSurname.setTypeface(medalSurname.typeface, Typeface.BOLD)
                medalRank.text = "STOPIEŃ"
                medalRank.setTypeface(medalRank.typeface, Typeface.BOLD)
                medalUnit.text = "JEDNOSTKA"
                medalUnit.setTypeface(medalUnit.typeface, Typeface.BOLD)
                medalYear.text = "ROK"
                medalYear.setTypeface(medalYear.typeface, Typeface.BOLD)
                medalNotes.text = "DODATKOWE INFORMACJE"
                medalNotes.setTypeface(medalYear.typeface, Typeface.BOLD)
                return
            }
            medalNumber.text = number.toString()
            medalName.text = name
            medalSurname.text = surname
            medalRank.text = rank
            medalUnit.text = unit
            if (year == 0L) {
                medalYear.text = ""
            } else {
                medalYear.text = (year ?: "").toString()
            }
            medalNotes.text = notes

            editButton.setOnClickListener {
                val navController = Navigation.findNavController(itemView)
                val medal = Medal(
                    -1,
                    number,
                    name,
                    surname,
                    rank,
                    unit,
                    year,
                    notes,
                    -1
                )
                val bundle: Bundle = Bundle()
                bundle.putParcelable("medal", medal)
                navController.navigate(R.id.EditScreen, bundle)
            }
        }

        companion object {
            fun create(parent: ViewGroup): MedalViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.medal_item, parent, false)
                return MedalViewHolder(view)
            }
        }
    }

    companion object {
        private val MEDAL_COMPARATOR = object : DiffUtil.ItemCallback<Medal>() {
            override fun areItemsTheSame(oldItem: Medal, newItem: Medal): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Medal, newItem: Medal): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name
            }

        }
    }
}