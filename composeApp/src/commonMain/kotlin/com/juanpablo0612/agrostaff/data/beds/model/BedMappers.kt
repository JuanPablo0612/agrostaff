package com.juanpablo0612.agrostaff.data.beds.model

import com.juanpablo0612.agrostaff.domain.models.Bed

fun BedModel.toDomain() = Bed(
    id = id,
    blockId = blockId,
    name = name,
    description = description
)

fun Bed.toModel() = BedModel(
    id = id,
    blockId = blockId,
    name = name,
    description = description
)