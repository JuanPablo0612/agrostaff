package com.juanpablo0612.agrostaff

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import agrostaff.composeapp.generated.resources.Res
import agrostaff.composeapp.generated.resources.compose_multiplatform
import androidx.compose.material3.Surface
import com.juanpablo0612.agrostaff.ui.theme.AgroStaffTheme
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        AgroStaffTheme {
            Surface(color = MaterialTheme.colorScheme.background) {

            }
        }
    }
}