package com.juanpablo0612.agrostaff.data.beds.remote

import com.juanpablo0612.agrostaff.data.beds.model.BedModel
import com.juanpablo0612.agrostaff.data.blocks.model.BlockModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

private const val BEDS_TABLE_NAME = "beds"

class BedsRemoteDataSource(private val supabase: SupabaseClient) {
    suspend fun createBed(bed: BedModel) = supabase
        .from(BEDS_TABLE_NAME)
        .insert(bed) {
            select()
        }
        .decodeSingleOrNull<BedModel>()

    suspend fun getAllBeds() = supabase
        .from(BEDS_TABLE_NAME)
        .select()
        .decodeList<BedModel>()

    suspend fun getAllBedsByBlockId(blockId: Int) = supabase
        .from(BEDS_TABLE_NAME)
        .select {
            filter {
                BedModel::blockId eq blockId
            }
        }
        .decodeList<BedModel>()

    suspend fun getBedById(id: Int) = supabase
        .from(BEDS_TABLE_NAME)
        .select {
            filter {
                BedModel::id eq id
            }
        }
        .decodeSingleOrNull<BedModel>()
}