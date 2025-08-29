package com.juanpablo0612.agrostaff.data.auth

import com.juanpablo0612.agrostaff.data.auth.remote.AuthRemoteDataSource

class AuthRepository(private val remoteDataSource: AuthRemoteDataSource) {
    suspend fun signIn(email: String, password: String) {
        remoteDataSource.signIn(email, password)
    }
}