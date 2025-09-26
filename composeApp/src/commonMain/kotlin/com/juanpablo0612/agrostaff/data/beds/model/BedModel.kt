package com.juanpablo0612.agrostaff.data.beds.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BedModel(
    @SerialName("id") val id: Int? = null,
    @SerialName("block_id") val blockId: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String
)
