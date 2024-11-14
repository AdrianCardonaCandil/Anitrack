package com.example.anitrack.data

import com.example.anitrack.network.AuthService
import com.example.anitrack.network.DatabaseService
import com.example.anitrack.network.FirebaseAuthService
import com.example.anitrack.network.FirebaseFirestoreService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface FirebaseContainer {
    val authService: AuthService
    val firestoreService: DatabaseService
}

class DefaultFirebaseContainer : FirebaseContainer {
    override val authService: AuthService by lazy {

        FirebaseAuthService(
            auth = Firebase.auth,
        )
    }

    override val firestoreService: DatabaseService by lazy {
        FirebaseFirestoreService(Firebase.firestore)
    }
}
