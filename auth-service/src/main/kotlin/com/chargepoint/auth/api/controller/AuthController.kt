package com.chargepoint.auth.api.controller

import com.chargepoint.auth.api.model.AuthRequest
import com.chargepoint.auth.api.model.AuthResponse
import com.chargepoint.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.awt.PageAttributes

@RestController
@RequestMapping("/auth")
class AuthController(
private val service: AuthService
) {

    @PostMapping
    fun authorize(@Valid @RequestBody request: AuthRequest): AuthResponse {
        return AuthResponse(valid = true)
    }
}
