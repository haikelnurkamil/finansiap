package org.d3if0070.finansiap.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.d3if0070.finansiap.firebase.GrupRepository
import org.d3if0070.finansiap.model.Grup

class GrupViewModel(val repository: GrupRepository) : ViewModel() {
    private val _currentGrup = MutableStateFlow<Grup?>(null)
    val currentGrup: StateFlow<Grup?> = _currentGrup
    private val _grupList = MutableStateFlow<List<Grup>>(emptyList())
    val grupList: StateFlow<List<Grup>> = _grupList

    private val _joinedGrupList = MutableStateFlow<List<Grup>>(emptyList())
    val joinedGrupList: StateFlow<List<Grup>> = _joinedGrupList

    private val _createdGrupList = MutableStateFlow<List<Grup>>(emptyList())
    val createdGrupList: StateFlow<List<Grup>> = _createdGrupList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchGrupList()
    }

    private fun fetchGrupList() {
        viewModelScope.launch {
            try {
                val list = repository.getAllGrup()
                _grupList.value = list
            } catch (e: Exception) {
                Log.e("GrupViewModel", "Error fetching grup list", e)
            }
        }
    }

    fun insert(namaGrup: String, kodeGrup: String, userId: String) {
        viewModelScope.launch {
            val id = repository.getNextId()
            val grup = Grup(id, namaGrup, kodeGrup, listOf(), userId)
            repository.insert(grup)
            fetchCreatedGrup(userId)
        }
    }

    fun getGrupById(grupId: String) {
        viewModelScope.launch {
            try {
                val grup = repository.getGrupById(grupId)
                _currentGrup.value = grup
            } catch (e: Exception) {
                Log.e("GrupViewModel", "Error fetching grup", e)
            }
        }
    }

    fun updateGrup(grup: Grup) {
        viewModelScope.launch {
            repository.updateGrup(grup)
            _currentGrup.value = grup
        }
    }

    fun joinGroup(userId: String, kodeGrup: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.joinGroup(userId, kodeGrup)
                if (success) {
                    fetchJoinedGrup(userId)
                    onSuccess()
                } else {
                    onFailure()
                }
            } catch (e: Exception) {
                Log.e("GrupViewModel", "Error joining grup", e)
                onFailure()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchJoinedGrup(userId: String) {
        viewModelScope.launch {
            try {
                val list = repository.getJoinedGrup(userId)
                _joinedGrupList.value = list
            } catch (e: Exception) {
                Log.e("GrupViewModel", "Error fetching joined grup items", e)
            }
        }
    }

    fun fetchCreatedGrup(userId: String) {
        viewModelScope.launch {
            try {
                val list = repository.getCreatedGrup(userId)
                _createdGrupList.value = list
            } catch (e: Exception) {
                Log.e("GrupViewModel", "Error fetching created grup items", e)
            }
        }
    }
}
