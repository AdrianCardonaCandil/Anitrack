package com.example.anitrack.data

import com.example.anitrack.network.AuthService

interface AuthRepository {
}

class AuthFirebaseRepository(val authService: AuthService) : AuthRepository {

}