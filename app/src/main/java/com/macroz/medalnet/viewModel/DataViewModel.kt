package com.macroz.medalnet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.repository.DataRepository
import com.macroz.medalnet.repository.DataRepository.AddMedalCallback

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    fun getAllMedals(): LiveData<List<Medal>> {
        return repository.getAllMedals()
    }

    fun searchMedalsByName(query: String): LiveData<List<Medal>> {
        return repository.getMedalsByName(query)
    }

    fun addMedal(medal: Medal, bearerToken: String, callback: AddMedalCallback) {
        repository.addMedal(medal, bearerToken, callback)
    }
}

class DataViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}