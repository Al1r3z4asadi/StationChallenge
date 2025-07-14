package com.chargepoint.session.sessionprocessor

import com.chargepoint.session.sessionprocessor.application.SessionListener
import com.chargepoint.session.sessionprocessor.application.SessionProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CoreConfig(
    @Value("\${auth-service.base-url}")
    private val authServiceBaseUrl: String
)
{
    @Bean
    fun sessionListener(sessionProcessor: SessionProcessor): SessionListener =
        SessionListener(sessionProcessor)

    @Bean
    fun webClientBuilder(): WebClient.Builder =
        WebClient.builder()

    @Bean
    fun authWebClient(builder: WebClient.Builder): WebClient =
        builder
            .baseUrl("http://auth-service")
            .build()
}