package org.d3if0070.finansiap.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if0070.finansiap.firebase.GrupRepository
import org.d3if0070.finansiap.viewmodel.GrupViewModel

class GrupViewModelFactory(private val repository: GrupRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GrupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GrupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
