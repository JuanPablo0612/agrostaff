package com.juanpablo0612.agrostaff.data.di

import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {
    single { HttpClient() }
}