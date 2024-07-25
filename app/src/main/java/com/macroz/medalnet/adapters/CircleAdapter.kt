package com.macroz.medalnet.adapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.macroz.medalnet.R

class CircleAdapter(private val context: Context, private val circleCount: Int) :
    RecyclerView.Adapter<CircleAdapter.CircleViewHolder>() {

    private val names = arrayOf("Search", "Add", "Edit")
    private val symbols = arrayOf(R.drawable.ic_search, R.drawable.ic_add, R.drawable.ic_edit)
    private val destinationIds = arrayOf(
        R.id.action_FirstFragment_to_SearchScreen,
        R.id.action_FirstFragment_to_AddScreen,
        R.id.action_FirstFragment_to_EditScreen
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.circle_item, parent, false)
        return CircleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CircleViewHolder, position: Int) {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

        // Calculate the size of each circle and the gap
        val gap = screenWidth / 12
        val circleSize = screenWidth / 4

        holder.bind(circleSize, gap, names[position], symbols[position], destinationIds[position])
    }

    class CircleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val circle: ImageView = view.findViewById(R.id.circle)
        private val symbol: ImageView = view.findViewById(R.id.symbol)
        private val name: TextView = view.findViewById(R.id.name)

        fun bind(circleSize: Int, gap: Int, actionName: String, symbolId: Int, destId: Int) {
            val layoutParams = circle.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = circleSize
            layoutParams.height = circleSize
            layoutParams.setMargins(gap / 2, 0, gap / 2, 0)
            circle.layoutParams = layoutParams
            name.text = actionName
            symbol.setImageDrawable(ContextCompat.getDrawable(itemView.context, symbolId))
            itemView.setOnClickListener {
                val navController: NavController = Navigation.findNavController(itemView)
                navController.navigate(destId)
            }
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }
}
