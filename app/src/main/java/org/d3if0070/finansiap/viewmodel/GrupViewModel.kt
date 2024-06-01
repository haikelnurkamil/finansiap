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
    private val _grupList = MutableStateFlow<List<Grup>>(emptyList())
    val gruplist: StateFlow<List<Grup>> get() = _grupList

    private val _editedGrup = MutableStateFlow(Grup())

    init {
        Log.d("GrupDetailViewModel", "ViewModel initialized")
        fetchGrupList()
    }

    private fun fetchGrupList() {
        viewModelScope.launch {
            Log.d("ShopDetailViewModel", "Fetching shop list")
            try {
                val list = repository.getAllGrup()
                _grupList.value = list
                list.forEach { grup ->
                    Log.d("ShopScreen", "Fetched shop: ${grup.namaGrup}")
                }
                Log.d("ShopDetailViewModel", "Fetched ${list.size} items")
            } catch (e: Exception) {
                Log.d("ShopDetailViewModel", "Error fetching data: ${e.message}")
            }
        }
    }

    fun insert(namaGrup: String, kodeGrup: String) {
        viewModelScope.launch {
            val id = repository.getNextId()
            val grup = Grup(id, namaGrup, kodeGrup)
            repository.insert(grup)
            fetchGrupList()
            Log.d("ShopDetailViewModel", "Inserted Grup with id: $id")
        }
    }

//    fun update(id: String, namaGrup: String, kodeGrup: String) {
//        viewModelScope.launch {
//            val grup = Grup(id, namaGrup, kodeGrup )
//            repository.update(grup)
//            fetchGrupList()
//            _editedGrup.value = grup
//            Log.d("ShopDetailViewModel", "Updated shop with id: $id")
//        }
//    }
//
//    fun delete(id: String) {
//        viewModelScope.launch {
//            repository.delete(id)
//            fetchGrupList()
//            Log.d("ShopDetailViewModel", "Deleted shop with id: $id")
//        }
//    }

    suspend fun getGrupById(id: String): Grup? {
        Log.d("ShopDetailViewModel", "Fetching shop with id: $id")
        return repository.getGrupById(id)
    }
}