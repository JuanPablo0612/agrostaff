package com.juanpablo0612.agrostaff.data.beds

import com.juanpablo0612.agrostaff.data.beds.model.toDomain
import com.juanpablo0612.agrostaff.data.beds.model.toModel
import com.juanpablo0612.agrostaff.data.beds.remote.BedsRemoteDataSource
import com.juanpablo0612.agrostaff.domain.models.Bed

class BedsRepository(private val remoteDataSource: BedsRemoteDataSource) {
    suspend fun createBed(bed: Bed) = try {
        val model = remoteDataSource.createBed(bed.toModel())
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getAllBeds() = try {
        val models = remoteDataSource.getAllBeds()
        Result.success(models.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getAllBedsByBlockId(blockId: Int) = try {
        val models = remoteDataSource.getAllBedsByBlockId(blockId)
        Result.success(models.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getBedById(id: Int) = try {
        val model = remoteDataSource.getBedById(id)
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun deleteBed(id: Int) = try {
        val model = remoteDataSource.deleteBed(id)
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }
}
