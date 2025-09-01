package com.juanpablo0612.agrostaff.data.users.model

import com.juanpablo0612.agrostaff.domain.models.User

fun UserModel.toDomain(): User = User(
    id = id,
    authUserId = authUserId ?: "",
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber.toString(),
    identityDocumentType = identityDocumentType,
    identityDocumentNumber = identityDocumentNumber.toString(),
    identityDocumentIssuedIn = identityDocumentIssuedIn,
    availability = availability,
    role = role,
    createdAt = createdAt
)

fun User.toModel(): UserModel = UserModel(
    id = id,
    authUserId = authUserId,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber.filter { it.isDigit() }.toLongOrNull() ?: 0L,
    identityDocumentType = identityDocumentType,
    identityDocumentNumber = identityDocumentNumber.filter { it.isDigit() }.toLongOrNull() ?: 0L,
    identityDocumentIssuedIn = identityDocumentIssuedIn,
    availability = availability,
    role = role,
    createdAt = createdAt
)
