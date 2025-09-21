package com.juanpablo0612.agrostaff.data.auth.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthRemoteDataSource(private val supabase: SupabaseClient) {
    suspend fun signIn(email: String, password: String) {
        val result = supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }
}