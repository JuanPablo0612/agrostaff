package com.juanpablo0612.agrostaff.data.blocks.remote

import com.juanpablo0612.agrostaff.data.blocks.model.BlockModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.decodeSingleOrNull
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.insert
import io.github.jan.supabase.postgrest.select

private const val BLOCKS_TABLE_NAME = "blocks"

class BlocksRemoteDataSource(private val supabase: SupabaseClient) {
    suspend fun createBlock(block: BlockModel) = supabase
        .from(BLOCKS_TABLE_NAME)
        .insert(block) {
            select()
        }
        .decodeSingleOrNull<BlockModel>()
}
