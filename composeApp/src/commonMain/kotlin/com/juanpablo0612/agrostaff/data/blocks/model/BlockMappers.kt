package com.juanpablo0612.agrostaff.data.blocks.model

import com.juanpablo0612.agrostaff.domain.models.Block

fun BlockModel.toDomain(): Block = Block(
    id = id,
    name = name,
    description = description,
)

fun Block.toModel(): BlockModel = BlockModel(
    id = id,
    name = name,
    description = description,
)
