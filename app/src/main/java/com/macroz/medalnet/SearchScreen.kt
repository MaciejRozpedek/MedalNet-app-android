package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.macroz.medalnet.adapters.MedalAdapter
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.databinding.SearchScreenBinding

class SearchScreen : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: SearchScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        m = activity as MainActivity
        _binding = SearchScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchScreenSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false
                val recyclerView = binding.searchScreenRecyclerView
                val adapter = MedalAdapter()
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(activity,
                    LinearLayoutManager.HORIZONTAL, false)
                val medals = m.dataViewModel.searchMedalsByName(newText)

                medals.observe(viewLifecycleOwner) { medals ->
                    // add new medal responsible for displaying descriptions of medal fields
                    val newMedal = Medal(-1, null, null, null, null, null, null, null, -1)
                    val updatedMedals = mutableListOf(newMedal)
                    updatedMedals.addAll(medals)
                    adapter.submitList(updatedMedals)
                }
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}