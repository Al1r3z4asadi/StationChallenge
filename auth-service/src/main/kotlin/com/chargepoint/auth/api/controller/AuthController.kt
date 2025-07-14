package com.chargepoint.auth.api.controller

import com.chargepoint.auth.api.model.AuthRequest
import com.chargepoint.auth.service.AuthService
import com.chargepoint.auth.service.AuthorizationStatus
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
    fun authorize(@Valid @RequestBody request: AuthRequest): AuthorizationStatus {
        return service.checkACL(request.driverToken)
    }
}
