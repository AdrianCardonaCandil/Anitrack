package com.example.anitrack.data

import com.example.anitrack.network.DatabaseResult
import com.example.anitrack.network.DatabaseService

interface DatabaseRepository {
    suspend fun <T> createDocument (
        collectionPath: DatabaseCollections,
        data: T,
        documentId: String?
    ) : DatabaseResult<String>

    suspend fun <T> readCollection (
        collectionPath: DatabaseCollections,
        model: Class<T>,
    ) : DatabaseResult<List<T>>

    suspend fun <T> readDocument (
        collectionPath: DatabaseCollections,
        model: Class<T>,
        documentId: String
    ) : DatabaseResult<T?>

    suspend fun updateDocument (
        collectionPath: DatabaseCollections,
        documentId: String,
        updates: Map<String, Any>
    ) : DatabaseResult<Boolean>

    suspend fun deleteDocument (
        collectionPath: DatabaseCollections,
        documentId: String
    ) : DatabaseResult<Boolean>

    suspend fun <T> filterCollection (
        collectionPath: DatabaseCollections,
        fieldName: String,
        value: Any,
        operation: DatabaseService.ComparisonType,
        model: Class<T>
    ) : DatabaseResult<List<T>>

}

class FirestoreFirebaseRepository(private val firestoreService: DatabaseService) : DatabaseRepository {
    override suspend fun <T> createDocument (
        collectionPath: DatabaseCollections,
        data: T,
        documentId: String?
    ) : DatabaseResult<String> =
        this.firestoreService.createDocument(
            collectionPath = collectionPath.name,
            data = data,
            documentId = documentId
        )

    override suspend fun <T> readCollection(
        collectionPath: DatabaseCollections,
        model: Class<T>
    ): DatabaseResult<List<T>> =
        this.firestoreService.readCollection(
            collectionPath = collectionPath.name,
            model = model
        )

    override suspend fun <T> readDocument(
        collectionPath: DatabaseCollections,
        model: Class<T>,
        documentId: String
    ): DatabaseResult<T?> =
        this.firestoreService.readDocument(
            collectionPath = collectionPath.name,
            model = model,
            documentId = documentId
        )

    override suspend fun updateDocument(
        collectionPath: DatabaseCollections,
        documentId: String,
        updates: Map<String, Any>
    ): DatabaseResult<Boolean> =
        this.firestoreService.updateDocument(
            collectionPath = collectionPath.name,
            documentId = documentId,
            updates = updates
        )

    override suspend fun deleteDocument(
        collectionPath: DatabaseCollections,
        documentId: String
    ): DatabaseResult<Boolean> =
        this.firestoreService.deleteDocument(
            collectionPath = collectionPath.name,
            documentId = documentId
        )

    override suspend fun <T> filterCollection(
        collectionPath: DatabaseCollections,
        fieldName: String,
        value: Any,
        operation: DatabaseService.ComparisonType,
        model: Class<T>
    ): DatabaseResult<List<T>> =
        this.firestoreService.filterCollection(
            collectionPath = collectionPath.name,
            fieldName = fieldName,
            value = value,
            operation = operation,
            model = model
        )
}

enum class DatabaseCollections {
    Contents, Characters, Users
}
