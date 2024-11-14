package com.example.anitrack.ui.lists

import com.example.anitrack.data.DatabaseCollections
import com.example.anitrack.data.DatabaseRepository
import com.example.anitrack.model.User
import com.example.anitrack.network.DatabaseResult

class ListHandler(private val databaseRepository: DatabaseRepository) {

    private suspend fun updateUserDocument(userId: String, listType: ListType, newList: List<String>): DatabaseResult<Boolean> {
        val fieldToUpdate = when (listType) {
            ListType.WATCHING -> "watching"
            ListType.COMPLETED -> "completed"
            ListType.PLAN_TO_WATCH -> "planToWatch"
            ListType.FAVORITES -> "favorites"
        }

        return databaseRepository.updateDocument(
            collectionPath = DatabaseCollections.Users,
            documentId = userId,
            updates = mapOf(fieldToUpdate to newList)
        )
    }

    suspend fun addToList(userId: String, contentId: String, targetList: ListType): DatabaseResult<Boolean> {
        val userResult = databaseRepository.readDocument(
            collectionPath = DatabaseCollections.Users,
            model = User::class.java,
            documentId = userId
        )

        if (userResult is DatabaseResult.Success) {
            val user = userResult.data
            if (user != null) {
                // Si la lista no es `favorites`, elimina el contenido de otras listas que no sean `favorites`
                val updatedUser = if (targetList != ListType.FAVORITES) {
                    removeContentFromOtherLists(user, contentId)
                } else user

                // Agrega el contenido a la lista objetivo
                val newList = when (targetList) {
                    ListType.WATCHING -> updatedUser.watching + contentId
                    ListType.COMPLETED -> updatedUser.completed + contentId
                    ListType.PLAN_TO_WATCH -> updatedUser.planToWatch + contentId
                    ListType.FAVORITES -> updatedUser.favorites + contentId
                }

                return updateUserDocument(userId, targetList, newList)
            }
        }

        return DatabaseResult.Failure(Exception("Failed to fetch user data"))
    }

    suspend fun removeFromList(userId: String, contentId: String, targetList: ListType): DatabaseResult<Boolean> {
        val userResult = databaseRepository.readDocument(
            collectionPath = DatabaseCollections.Users,
            model = User::class.java,
            documentId = userId
        )

        if (userResult is DatabaseResult.Success) {
            val user = userResult.data
            if (user != null) {
                // Elimina el contenido de la lista seleccionada
                val newList = when (targetList) {
                    ListType.WATCHING -> user.watching - contentId
                    ListType.COMPLETED -> user.completed - contentId
                    ListType.PLAN_TO_WATCH -> user.planToWatch - contentId
                    ListType.FAVORITES -> user.favorites - contentId
                }

                return updateUserDocument(userId, targetList, newList)
            }
        }

        return DatabaseResult.Failure(Exception("Failed to fetch user data"))
    }

    private fun removeContentFromOtherLists(user: User, contentId: String): User {
        // Elimina `contentId` de todas las listas excepto `favorites`
        return user.copy(
            watching = user.watching.filter { it != contentId },
            completed = user.completed.filter { it != contentId },
            planToWatch = user.planToWatch.filter { it != contentId },
            favorites = user.favorites // `favorites` mantiene `contentId` si ya está presente
        )
    }

    enum class ListType {
        WATCHING, COMPLETED, PLAN_TO_WATCH, FAVORITES
    }
}
