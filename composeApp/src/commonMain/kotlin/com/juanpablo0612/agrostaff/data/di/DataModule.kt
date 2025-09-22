package com.juanpablo0612.agrostaff.data.di

import com.juanpablo0612.agrostaff.Sekret
import com.juanpablo0612.agrostaff.data.auth.AuthRepository
import com.juanpablo0612.agrostaff.data.auth.remote.AuthRemoteDataSource
import com.juanpablo0612.agrostaff.data.blocks.BlocksRepository
import com.juanpablo0612.agrostaff.data.blocks.remote.BlocksRemoteDataSource
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { HttpClient() }
    single {
        createSupabaseClient(
            supabaseUrl = Sekret.supabaseUrl("com.juanpablo0612.agrostaff")!!,
            supabaseKey = Sekret.supabaseKey("com.juanpablo0612.agrostaff")!!
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
    singleOf(::AuthRemoteDataSource)
    singleOf(::BlocksRemoteDataSource)
    singleOf(::AuthRepository)
    singleOf(::BlocksRepository)
}