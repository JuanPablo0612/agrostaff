package com.juanpablo0612.agrostaff.ui.di

import com.juanpablo0612.agrostaff.ui.auth.sign_in.SignInViewModel
import com.juanpablo0612.agrostaff.ui.beds.add.AddBedViewModel
import com.juanpablo0612.agrostaff.ui.beds.list.BedListViewModel
import com.juanpablo0612.agrostaff.ui.blocks.add.AddBlockViewModel
import com.juanpablo0612.agrostaff.ui.blocks.list.BlockListViewModel
import com.juanpablo0612.agrostaff.ui.users.add.AddUserViewModel
import com.juanpablo0612.agrostaff.ui.users.list.UserListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    singleOf(::SignInViewModel)
    singleOf(::AddBlockViewModel)
    singleOf(::BlockListViewModel)
    singleOf(::AddBedViewModel)
    singleOf(::BedListViewModel)
    singleOf(::AddUserViewModel)
    singleOf(::UserListViewModel)
}
