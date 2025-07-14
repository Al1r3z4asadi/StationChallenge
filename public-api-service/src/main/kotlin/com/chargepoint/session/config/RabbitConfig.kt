package com.chargepoint.session.config
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {
    companion object {
        const val EXCHANGE = "commands-exchange"
        const val QUEUE    = "commands-queue"
        const val ROUTING  = "commands-routing"
    }

    @Bean
    fun commandExchange(): DirectExchange =
        DirectExchange(EXCHANGE, true, false)

    @Bean
    fun commandQueue(): Queue =
        Queue(QUEUE, true)

    @Bean
    fun binding(
        queue: Queue,
        exchange: DirectExchange
    ): Binding =
        BindingBuilder
            .bind(queue)
            .to(exchange)
            .with(ROUTING)

    @Bean
    fun jackson2JsonMessageConverter() = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(cf: ConnectionFactory): RabbitTemplate =
        RabbitTemplate(cf).apply {
            messageConverter = jackson2JsonMessageConverter()
        }
}