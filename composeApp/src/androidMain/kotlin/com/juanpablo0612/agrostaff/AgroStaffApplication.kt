package com.juanpablo0612.agrostaff

import android.app.Application
import dev.datlag.sekret.NativeLoader
import dev.datlag.sekret.RequestedApi

class AgroStaffApplication : Application() {
    @OptIn(RequestedApi::class)
    override fun onCreate() {
        super.onCreate()
        NativeLoader.loadLibrary("sekret")
    }
}