package com.example.anitrack.data

import android.net.Uri
import com.example.anitrack.network.DatabaseResult
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

interface StorageRepository {
    suspend fun uploadProfilePicture(userId: String, imageUri: Uri): DatabaseResult<String>
}


class FirebaseStorageRepository(private val storage: FirebaseStorage) : StorageRepository {
    override suspend fun uploadProfilePicture(userId: String, imageUri: Uri): DatabaseResult<String> {
        return try {
            val fileRef = storage.reference.child("profilePictures/$userId.jpg")
            val uploadTask = fileRef.putFile(imageUri).await()
            val downloadUrl = fileRef.downloadUrl.await().toString()
            DatabaseResult.Success(downloadUrl)
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }
}
