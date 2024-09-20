package com.macroz.medalnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macroz.medalnet.adapters.MedalAdapter
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.databinding.SearchScreenBinding

class SearchScreen : Fragment() {

    private lateinit var m: MainActivity
    private var _binding: SearchScreenBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedalAdapter

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
        recyclerView = binding.searchScreenRecyclerView
        adapter = MedalAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL, false)

        binding.searchScreenSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.submitList(emptyList())
                    return false
                }
                val medals = m.dataViewModel.searchMedalsByName(newText)
                observeMedals(medals)
                return false
            }
        })

        binding.byNumberSearchView.setOnQueryTextListener(object :
        SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapter.submitList(emptyList())
                    return false
                }
                val medals = m.dataViewModel.searchMedalsByNumber(newText)
                observeMedals(medals)
                return false
            }
        })
    }

    // observe medals and display them in the recycler view
    // if no results found, display no results text view
    private fun observeMedals(medals: LiveData<List<Medal>>) {
        medals.observe(viewLifecycleOwner) { medals ->
            if (medals.isEmpty()) {
                binding.noResultsTextView.text = "No results found"
                recyclerView.visibility = View.GONE
                binding.noResultsTextView.visibility = View.VISIBLE
                return@observe
            }
            // -2 equals no response from server
            if (medals[0].id == -1L) {
                binding.noResultsTextView.text = "No response from server"
                recyclerView.visibility = View.GONE
                binding.noResultsTextView.visibility = View.VISIBLE
                return@observe
            }
            recyclerView.visibility = View.VISIBLE
            binding.noResultsTextView.visibility = View.GONE
            // add new medal responsible for displaying descriptions of medal fields
            val newMedal = Medal(-1, null, null, null, null, null, null, null, -1)
            val updatedMedals = mutableListOf(newMedal)
            updatedMedals.addAll(medals)
            adapter.submitList(updatedMedals)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}