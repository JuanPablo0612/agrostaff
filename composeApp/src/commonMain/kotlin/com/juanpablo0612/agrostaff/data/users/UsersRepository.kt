package com.juanpablo0612.agrostaff.data.users

import com.juanpablo0612.agrostaff.data.users.model.toDomain
import com.juanpablo0612.agrostaff.data.users.model.toModel
import com.juanpablo0612.agrostaff.data.users.remote.UsersRemoteDataSource
import com.juanpablo0612.agrostaff.domain.models.CreateUser
import com.juanpablo0612.agrostaff.domain.models.User

class UsersRepository(private val remoteDataSource: UsersRemoteDataSource) {
    suspend fun getUserByAuthId(authId: String) = try {
        val model = remoteDataSource.getUserByAuthId(authId)
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getUserById(id: Int) = try {
        val model = remoteDataSource.getUserById(id)
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getAllUsers() = try {
        val models = remoteDataSource.getAllUsers()
        Result.success(models.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getAllUsersByIds(ids: List<Int>) = try {
        val models = remoteDataSource.getAllUsersByIds(ids)
        Result.success(models.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun createUser(user: CreateUser) = try {
        val model = remoteDataSource.createUser(user.toModel())
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun deleteUser(id: Int) = try {
        val model = remoteDataSource.deleteUser(id)
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }
}


