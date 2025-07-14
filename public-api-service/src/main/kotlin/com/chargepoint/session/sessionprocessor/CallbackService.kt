package com.chargepoint.session.sessionprocessor


import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration

@Service
class CallbackService(
    private val webClientBuilder: WebClient.Builder
) {
    private val log = LoggerFactory.getLogger(CallbackService::class.java)

    fun sendDecision(callbackUrl: URI, request: CallbackRequest) {
        webClientBuilder.build()
            .post()
            .uri(callbackUrl.toString())
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void::class.java)
            .timeout(Duration.ofSeconds(3))
            .doOnError { ex ->
                log.error("Failed to send callback to $callbackUrl: ${ex.message}")
            }
            .onErrorResume { Mono.empty() }
            .block()
    }
}