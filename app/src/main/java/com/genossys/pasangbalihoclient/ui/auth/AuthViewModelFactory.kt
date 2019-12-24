package com.genossys.pasangbalihoclient.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository

class AuthViewModelFactory(
    private val clientRepository: ClientRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(clientRepository) as T
    }
}