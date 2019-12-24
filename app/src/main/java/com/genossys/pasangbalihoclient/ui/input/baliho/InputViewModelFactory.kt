package com.genossys.pasangbalihoclient.ui.input.baliho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository

class InputViewModelFactory(
    private val repository: BalihoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InputViewModel(repository) as T
    }
}