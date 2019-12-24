package com.genossys.pasangbalihoclient.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository

class HomeViewModelFactory(
    private val repository: BalihoRepository,
    private val repositoryTransaksi: TransaksiRepository,
    private val clientRepository: ClientRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository, repositoryTransaksi, clientRepository) as T
    }
}