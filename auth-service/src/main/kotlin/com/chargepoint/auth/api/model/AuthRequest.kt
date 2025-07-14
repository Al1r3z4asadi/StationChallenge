package com.chargepoint.auth.api.model
import jakarta.validation.constraints.NotBlank

data class AuthRequest(
    @field:NotBlank
    val driverToken: String
)