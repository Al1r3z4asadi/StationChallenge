package com.chargepoint.session.sessionprocessor

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Service
class AuthService(
    private val authWebClient: WebClient
) {
    fun authenticate(driverToken: String): AuthResponse? {
        return try {
            authWebClient.post()
                .uri("/auth")
                .bodyValue(mapOf("driverToken" to driverToken))
                .retrieve()
                .bodyToMono(AuthResponse::class.java)
                .timeout(Duration.ofSeconds(5))
                .block()
        } catch (ex: Exception) {
            null
        }
    }
}