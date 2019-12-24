package com.genossys.pasangbalihoclient.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.NewsRepository

class NewsViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}