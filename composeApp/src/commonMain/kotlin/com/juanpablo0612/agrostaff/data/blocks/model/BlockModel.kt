package com.juanpablo0612.agrostaff.data.blocks.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockModel(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
)
