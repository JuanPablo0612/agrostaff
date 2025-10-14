package com.juanpablo0612.agrostaff.data.users.model

import com.juanpablo0612.agrostaff.domain.models.CreateUser
import com.juanpablo0612.agrostaff.domain.models.User

fun UserModel.toDomain(): User = User(
    id = id,
    authUserId = authUserId ?: "",
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    identityDocumentType = identityDocumentType,
    identityDocumentNumber = identityDocumentNumber,
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
    phoneNumber = phoneNumber,
    identityDocumentType = identityDocumentType,
    identityDocumentNumber = identityDocumentNumber,
    identityDocumentIssuedIn = identityDocumentIssuedIn,
    availability = availability,
    role = role,
    createdAt = createdAt
)

fun CreateUser.toModel(): CreateUserModel = CreateUserModel(
    authUserId = authUserId?.takeIf { it.isNotBlank() },
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    identityDocumentType = identityDocumentType,
    identityDocumentNumber = identityDocumentNumber,
    identityDocumentIssuedIn = identityDocumentIssuedIn,
    availability = availability,
    role = role,
)
