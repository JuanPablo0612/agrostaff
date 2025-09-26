package com.juanpablo0612.agrostaff.data.blocks

import com.juanpablo0612.agrostaff.data.blocks.model.toDomain
import com.juanpablo0612.agrostaff.data.blocks.model.toModel
import com.juanpablo0612.agrostaff.data.blocks.remote.BlocksRemoteDataSource
import com.juanpablo0612.agrostaff.domain.models.Block

class BlocksRepository(private val remoteDataSource: BlocksRemoteDataSource) {
    suspend fun createBlock(block: Block) = try {
        val model = remoteDataSource.createBlock(block.toModel())
        Result.success(model?.toDomain())
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }

    suspend fun getAllBlocks() = try {
        val models = remoteDataSource.getAllBlocks()
        Result.success(models.map { it.toDomain() })
    } catch (e: Exception) {
        Result.failure(e as Throwable)
    }
}
