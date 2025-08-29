package com.juanpablo0612.agrostaff

import com.juanpablo0612.agrostaff.data.di.dataModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
}