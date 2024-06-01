package org.d3if0070.finansiap.firebase
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.d3if0070.finansiap.model.Grup


class GrupRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("grup")

    suspend fun insert(grup: Grup): String {
        val document = collection.document()
        grup.gid = document.id
        document.set(grup).await()
        Log.d("GrupRepository", "Inserted Grup with id: ${grup.gid}")
        return grup.gid
    }

//    suspend fun update(grup: Grup) {
//        val document = collection.document(grup.gid)
//        document.set(grup).await()
//        Log.d("GrupRepository", "Updated shop with id: ${grup.gid}")
//    }

//    suspend fun delete(id: String) {
//        val document = collection.document(id)
//        document.delete().await()
//        Log.d("GrupRepository", "Deleted shop with id: $id")
//    }

    suspend fun getGrupById(id: String): Grup? {
        val document = collection.document(id).get().await()
        val grup = document.toObject(Grup::class.java)
        Log.d("GrupRepository", "Fetched grup with id: $id")
        return grup
    }

    suspend fun getAllGrup(): List<Grup> {
        return try {
            val documents = collection.get().await().documents
            Log.d("grupRepository", "Fetched ${documents.size} grup items")
            documents.mapNotNull { it.toObject(Grup::class.java) }
        } catch (e: Exception) {
            Log.e("grupRepository", "Error fetching grup items", e)
            emptyList()
        }
    }

    fun getNextId(): String {
        val document = collection.document()
        return document.id
    }
}