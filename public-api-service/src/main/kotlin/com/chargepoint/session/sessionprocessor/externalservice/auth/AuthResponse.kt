package com.chargepoint.session.sessionprocessor.externalservice.auth

data class AuthResponse(
    val value: String,
)


enum class AuthorizationStatus {
    ALLOWED,
    NOT_ALLOWED,
    UNKNOWN,
    INVALID
}