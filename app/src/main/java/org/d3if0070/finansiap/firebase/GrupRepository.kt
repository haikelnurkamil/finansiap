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

    suspend fun getGrupById(id: String): Grup? {
        val document = collection.document(id).get().await()
        val grup = document.toObject(Grup::class.java)
        Log.d("GrupRepository", "Fetched grup with id: $id")
        return grup
    }

    suspend fun getAllGrup(): List<Grup> {
        return try {
            val documents = collection.get().await().documents
            Log.d("GrupRepository", "Fetched ${documents.size} grup items")
            documents.mapNotNull { it.toObject(Grup::class.java) }
        } catch (e: Exception) {
            Log.e("GrupRepository", "Error fetching grup items", e)
            emptyList()
        }
    }

    suspend fun getJoinedGrup(userId: String): List<Grup> {
        val grupList = mutableListOf<Grup>()
        val querySnapshot = collection.whereArrayContains("joinedUsers", userId).get().await()
        for (document in querySnapshot.documents) {
            val grup = document.toObject(Grup::class.java)
            if (grup != null) {
                grupList.add(grup)
            }
        }
        Log.d("ABID", "getJoinedGrup: $grupList")
        return grupList
    }

    suspend fun getCreatedGrup(userId: String): List<Grup> {
        val grupList = mutableListOf<Grup>()
        val querySnapshot = collection.whereEqualTo("bendahara", userId).get().await()
        for (document in querySnapshot.documents) {
            val grup = document.toObject(Grup::class.java)
            if (grup != null) {
                grupList.add(grup)
            }
        }
        Log.d("ABID", "getCreatedGrup: $grupList")
        return grupList
    }

    suspend fun updateGrup(grup: Grup) {
        val document = collection.document(grup.gid)
        document.set(grup).await()
    }


    suspend fun joinGroup(userId: String, kodeGrup: String): Boolean {
        return try {
            val querySnapshot = collection.whereEqualTo("kodeGrup", kodeGrup).get().await()
            if (!querySnapshot.isEmpty) {
                val grupDocument = querySnapshot.documents.first()
                val groupId = grupDocument.id
                val grup = grupDocument.toObject(Grup::class.java)

                Log.d("GrupRepository", "Group found with id: $groupId")

                val updatedJoinedUsers = grup?.joinedUsers?.toMutableList().apply {
                    this?.add(userId)
                }

                grup?.joinedUsers = updatedJoinedUsers ?: listOf()

                grupDocument.reference.set(grup!!).await()
                Log.d("GrupRepository", "User $userId joined grup with kode: $kodeGrup")
                true
            } else {
                Log.d("GrupRepository", "Grup with kode: $kodeGrup not found")
                false
            }
        } catch (e: Exception) {
            Log.e("GrupRepository", "Error joining grup", e)
            false
        }
    }

    fun getNextId(): String {
        val document = collection.document()
        return document.id
    }
}
