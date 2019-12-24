package com.genossys.pasangbalihoclient.ui.baliho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.repository.KategoriRepository
import com.genossys.pasangbalihoclient.data.db.repository.KotaRepository

class BalihoViewModelFactory(
    private val balihoRepository: BalihoRepository,
    val kotaRepository: KotaRepository,
    val kategoriRepository: KategoriRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BalihoViewModel(balihoRepository, kotaRepository, kategoriRepository) as T
    }
}