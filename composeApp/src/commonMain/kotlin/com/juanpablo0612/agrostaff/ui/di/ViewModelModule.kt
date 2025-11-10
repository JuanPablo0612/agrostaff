package com.juanpablo0612.agrostaff.ui.di

import com.juanpablo0612.agrostaff.ui.auth.sign_in.SignInViewModel
import com.juanpablo0612.agrostaff.ui.beds.add.AddBedViewModel
import com.juanpablo0612.agrostaff.ui.beds.detail.BedDetailViewModel
import com.juanpablo0612.agrostaff.ui.beds.list.BedListViewModel
import com.juanpablo0612.agrostaff.ui.blocks.add.AddBlockViewModel
import com.juanpablo0612.agrostaff.ui.blocks.detail.BlockDetailViewModel
import com.juanpablo0612.agrostaff.ui.blocks.list.BlockListViewModel
import com.juanpablo0612.agrostaff.ui.users.add.AddUserViewModel
import com.juanpablo0612.agrostaff.ui.users.list.UserListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::AddBlockViewModel)
    viewModelOf(::BlockListViewModel)
    viewModelOf(::BlockDetailViewModel)
    viewModelOf(::BedDetailViewModel)
    viewModelOf(::AddBedViewModel)
    viewModelOf(::BedListViewModel)
    viewModelOf(::AddUserViewModel)
    viewModelOf(::UserListViewModel)
}
