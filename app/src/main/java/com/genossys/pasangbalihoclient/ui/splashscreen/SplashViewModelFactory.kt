package com.genossys.pasangbalihoclient.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository

class SplashViewModelFactory(
    private val clientRepository: ClientRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(clientRepository) as T
    }
}