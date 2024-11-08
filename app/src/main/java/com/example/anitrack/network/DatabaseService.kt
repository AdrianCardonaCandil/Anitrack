package com.example.anitrack.network

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

sealed interface DatabaseResult<out T> {
    data class Success<out T>(val data: T) : DatabaseResult<T>
    data class Failure(val error: Exception) : DatabaseResult<Nothing>
}

interface DatabaseService {
    /** Creates a new document in the specified collection. If documentId is
     * supplied, it will be used as the document id. Otherwise, firestore will
     * automatically generate it by itself.
     * @param collectionPath Collection route
     * @param data Object to store in collection
     * @param documentId Optional id for the document
     * @return Success or failure result of the operation
     * */

    suspend fun <T> createDocument(
        collectionPath: String,
        data: T,
        documentId: String? = null
    ) : DatabaseResult<String>

    /** Reads all the documents stored on a collection.
     * @param collectionPath Collection route
     * @param model Model on which the documents will be stored
     * @return Success or failure result of the operation. If the operation
     * results in success, a list of models with the data will be returned.
     * */

    suspend fun <T> readCollection(
        collectionPath: String,
        model: Class<T>
    ) : DatabaseResult<List<T>>

    /** Reads a document specified by id.
     * @param collectionPath Collection route
     * @param model Model on which the document will be stored
     * @param documentId Document id
     * @return Success or failure of the operation. If the operation
     * results in success, a model with the data will be returned.
     */

    suspend fun <T> readDocument(
        collectionPath: String,
        model: Class<T>,
        documentId: String
    ) : DatabaseResult<T?>

    /** Updated a concrete document specified by id. If the document doesn't exists,
     * no action is performed.
     * @param collectionPath Collection route
     * @param documentId Document id
     * @param updates Map with the keys and values to update on the document
     * @return Success or failure of the operation.
     */

    suspend fun updateDocument(
        collectionPath: String,
        documentId: String,
        updates: Map<String, Any>
    ) : DatabaseResult<Boolean>

    /** Deletes a concrete document specified by id.
     * @param collectionPath Collection route
     * @param documentId Document id
     * @return Success or failure of the operation.
     * */

    suspend fun deleteDocument(
        collectionPath: String,
        documentId: String
    ) : DatabaseResult<Boolean>

    suspend fun <T> filterCollection(
        collectionPath: String,
        fieldName: String,
        value: Any,
        operation: ComparisonType = ComparisonType.Equals,
        model: Class<T>
    ) : DatabaseResult<List<T>>

    enum class ComparisonType {
        Equals, Less, Greater
    }

}

class FirebaseFirestoreService(val firestore: FirebaseFirestore) : DatabaseService {
    override suspend fun <T> createDocument(
        collectionPath: String,
        data: T,
        documentId: String?
    ): DatabaseResult<String> {
        return try {
            val documentRef = if (documentId != null) {
                firestore.collection(collectionPath).document(documentId).set(data as Any).await()
                firestore.collection(collectionPath).document(documentId)
            } else {
                firestore.collection(collectionPath).add(data as Any).await()
            }
            DatabaseResult.Success(documentRef.id)
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }

    override suspend fun <T> readCollection(
        collectionPath: String,
        model: Class<T>
    ): DatabaseResult<List<T>> {
        return try {
            DatabaseResult.Success(
                firestore.collection(collectionPath).get().await().toObjects(model)
            )
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }

    override suspend fun <T> readDocument(
        collectionPath: String,
        model: Class<T>,
        documentId: String
    ): DatabaseResult<T?> {
        return try {
            DatabaseResult.Success(
                firestore.collection(collectionPath)
                    .document(documentId)
                    .get()
                    .await()
                    .toObject(model)
            )
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }

    override suspend fun updateDocument(
        collectionPath: String,
        documentId: String,
        updates: Map<String, Any>
    ): DatabaseResult<Boolean> {
        return try {
            firestore.collection(collectionPath).document(documentId).update(updates).await()
            DatabaseResult.Success(true)
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }

    override suspend fun deleteDocument(
        collectionPath: String,
        documentId: String
    ): DatabaseResult<Boolean> {
        return try {
            firestore.collection(collectionPath).document(documentId).delete().await()
            DatabaseResult.Success(true)
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }

    override suspend fun <T> filterCollection(
        collectionPath: String,
        fieldName: String,
        value: Any,
        operation: DatabaseService.ComparisonType,
        model: Class<T>
    ): DatabaseResult<List<T>> {
        return try {
            val path = firestore.collection(collectionPath)
            val query = when (operation) {
                DatabaseService.ComparisonType.Equals -> path.whereLessThan(fieldName, value)
                DatabaseService.ComparisonType.Less -> path.whereEqualTo(fieldName, value)
                DatabaseService.ComparisonType.Greater -> path.whereGreaterThan(fieldName, value)
            }
            DatabaseResult.Success(query.get().await().toObjects(model))
        } catch (e: Exception) {
            DatabaseResult.Failure(e)
        }
    }
}