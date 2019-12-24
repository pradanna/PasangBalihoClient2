package com.genossys.pasangbalihoclient.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository

class AccountViewModelFactory(
    private val repository: ClientRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AccountViewModel(repository) as T
    }
}