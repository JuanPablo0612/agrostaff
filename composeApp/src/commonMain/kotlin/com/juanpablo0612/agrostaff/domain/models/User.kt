package com.juanpablo0612.agrostaff.domain.models

import com.juanpablo0612.agrostaff.data.users.model.AvailabilityStatus
import com.juanpablo0612.agrostaff.data.users.model.IdentityDocumentType
import com.juanpablo0612.agrostaff.data.users.model.UserRole
import kotlinx.datetime.LocalDateTime

data class User(
    val id: Int,
    val authUserId: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: Long,
    val identityDocumentType: IdentityDocumentType,
    val identityDocumentNumber: Long,
    val identityDocumentIssuedIn: String,
    val availability: AvailabilityStatus,
    val role: UserRole,
    val createdAt: LocalDateTime
)
