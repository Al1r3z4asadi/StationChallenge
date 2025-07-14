package com.chargepoint.session.common


import com.chargepoint.session.config.RabbitConfig
import com.chargepoint.session.messagecontract.Message

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class RabbitMessageDispatcher(
    private val rabbitTemplate: RabbitTemplate
) : MessageDispatcher {
    override fun <T : Message> dispatch(message: T) {
        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE,
            RabbitConfig.ROUTING,
            message
        )
    }
}