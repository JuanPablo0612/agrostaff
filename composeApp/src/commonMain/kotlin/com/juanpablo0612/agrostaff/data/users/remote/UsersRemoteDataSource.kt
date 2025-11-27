package com.juanpablo0612.agrostaff.data.users.remote

import com.juanpablo0612.agrostaff.data.users.model.CreateUserModel
import com.juanpablo0612.agrostaff.data.users.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

private const val USERS_TABLE_NAME = "users"

class UsersRemoteDataSource(private val supabase: SupabaseClient) {
    suspend fun getUserByAuthId(authId: String) = supabase
        .from(USERS_TABLE_NAME)
        .select {
            filter {
                UserModel::authUserId eq authId
            }
        }
        .decodeSingleOrNull<UserModel>()

    suspend fun getUserById(id: Int) = supabase
        .from(USERS_TABLE_NAME)
        .select {
            filter {
                UserModel::id eq id
            }
        }
        .decodeSingleOrNull<UserModel>()

    suspend fun getAllUsers() = supabase
        .from(USERS_TABLE_NAME)
        .select()
        .decodeList<UserModel>()

    suspend fun getAllUsersByIds(ids: List<Int>) = supabase
        .from(USERS_TABLE_NAME)
        .select {
            filter {
                UserModel::id contains ids
            }
        }
        .decodeList<UserModel>()

    suspend fun createUser(user: CreateUserModel) = supabase
        .from(USERS_TABLE_NAME)
        .insert(user) {
            select()
        }
        .decodeSingleOrNull<UserModel>()

    suspend fun updateUser(userId: Int, user: UserModel) = supabase
        .from(USERS_TABLE_NAME)
        .update(user) {
            filter {
                UserModel::id eq userId
            }
            select()
        }
        .decodeSingleOrNull<UserModel>()

    suspend fun deleteUser(id: Int) = supabase
        .from(USERS_TABLE_NAME)
        .delete {
            filter {
                UserModel::id eq id
            }
            select()
        }
        .decodeSingleOrNull<UserModel>()
}
