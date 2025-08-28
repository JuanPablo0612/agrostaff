package com.juanpablo0612.agrostaff

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform