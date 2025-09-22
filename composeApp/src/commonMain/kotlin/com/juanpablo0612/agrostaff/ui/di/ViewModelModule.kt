package com.juanpablo0612.agrostaff.ui.di

import com.juanpablo0612.agrostaff.ui.auth.sign_in.SignInViewModel
import com.juanpablo0612.agrostaff.ui.blocks.add.AddBlockViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    singleOf(::SignInViewModel)
    singleOf(::AddBlockViewModel)
}

