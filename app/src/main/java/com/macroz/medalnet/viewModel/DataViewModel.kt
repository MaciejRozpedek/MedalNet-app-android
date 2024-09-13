package com.macroz.medalnet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.macroz.medalnet.data.Medal
import com.macroz.medalnet.repository.DataRepository
import com.macroz.medalnet.repository.DataRepository.AddMedalCallback
import kotlinx.coroutines.launch

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean?>()
    val loginSuccess: LiveData<Boolean?>
        get() = _loginSuccess

    fun getAllMedals(): LiveData<List<Medal>> {
        return repository.getAllMedals()
    }

    fun searchMedalsByName(query: String): LiveData<List<Medal>> {
        return repository.getMedalsByName(query)
    }

    fun addMedal(medal: Medal, callback: AddMedalCallback) {
        repository.addMedal(medal, callback)
    }

    fun login(emailOrUsername: String, emailOrUsernameValue: String, password: String) {
        viewModelScope.launch {
            val success = repository.login(emailOrUsername, emailOrUsernameValue, password)
            if (success) {
                repository.saveEmail(emailOrUsernameValue)
                repository.saveUsername(emailOrUsernameValue)
                repository.savePassword(password)
            }
            _loginSuccess.value = success
        }
    }

    fun loginHandled() {
        _loginSuccess.value = null
    }

    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            val success = repository.register(email, username, password)
            if (success) {
                repository.saveEmail(email)
                repository.saveUsername(username)
                repository.savePassword(password)
            }
            _loginSuccess.value = success
        }
    }

    fun registerHandled() {
        _loginSuccess.value = null
    }

    fun logout() {
        repository.saveToken("")
    }

    fun saveEmail(email: String) {
        repository.saveEmail(email)
    }

    fun saveUsername(username: String) {
        repository.saveUsername(username)
    }

    fun savePassword(password: String) {
        repository.savePassword(password)
    }

    fun getEmail(): String {
        return repository.getEmail()
    }

    fun getUsername(): String {
        return repository.getUsername()
    }

    fun getPassword(): String {
        return repository.getPassword()
    }

    fun saveToken(token: String) {
        repository.saveToken(token)
    }

    fun getToken(): String {
        return repository.getToken()
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