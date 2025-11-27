package com.juanpablo0612.agrostaff.data.users.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("id") val id: Int? = null,
    @SerialName("auth_user_id") val authUserId: String? = null,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("phone_number") val phoneNumber: Long,
    @SerialName("identity_document_type") val identityDocumentType: IdentityDocumentType,
    @SerialName("identity_document_number") val identityDocumentNumber: Long,
    @SerialName("identity_document_issued_in") val identityDocumentIssuedIn: String,
    @SerialName("availability") val availability: AvailabilityStatus,
    @SerialName("role") val role: UserRole,
    @SerialName("created_at") val createdAt: LocalDateTime
)
