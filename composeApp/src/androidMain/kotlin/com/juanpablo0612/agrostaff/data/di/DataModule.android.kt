package com.juanpablo0612.agrostaff.data.di

import com.juanpablo0612.agrostaff.BuildConfig

actual object AppSecrets {
    actual val supabaseUrl: String = BuildConfig.SUPABASE_URL
    actual val supabaseKey: String = BuildConfig.SUPABASE_KEY
}