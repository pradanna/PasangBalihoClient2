package com.genossys.pasangbalihoclient.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository

class DetailBalihoViewModelFactory(
    private val repository: BalihoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailBalihoViewModel(repository) as T
    }
}