package com.leofranc.violao_suite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leofranc.violao_suite.repository.TablaturaRepository

class TablaturaViewModelFactory(
    private val repository: TablaturaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TablaturaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TablaturaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
