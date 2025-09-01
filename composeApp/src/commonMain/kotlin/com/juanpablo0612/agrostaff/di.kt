package com.juanpablo0612.agrostaff

import com.juanpablo0612.agrostaff.data.di.dataModule
import com.juanpablo0612.agrostaff.ui.di.viewModelModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule, viewModelModule)
}