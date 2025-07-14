package com.chargepoint.session.sessionprocessor.externalservice.auth

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration


@Service
class AuthServiceAdapter(
    private val authWebClient: WebClient
) {
    fun authenticate(driverToken: String): String {
        return try {
            val authStatus = authWebClient.post()
                .uri("/auth")
                .bodyValue(mapOf("driverToken" to driverToken))
                .retrieve()
                .bodyToMono(AuthorizationStatus::class.java)
                .timeout(Duration.ofSeconds(5))
                .block()

            authStatus?.name?.lowercase() ?: "unknown"

        } catch (ex: Exception) {
            "unknown"
        }
    }
}