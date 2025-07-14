package com.chargepoint.session.sessionprocessor

import com.chargepoint.session.config.RabbitConfig
import com.chargepoint.session.messagecontract.commands.Command
import org.springframework.amqp.rabbit.annotation.RabbitListener

class SessionListener(
    private val sessionProcessor: SessionProcessor
) {
    @RabbitListener(queues = [RabbitConfig.QUEUE])
    fun onCommand(cmd: Command) {
        sessionProcessor.process(cmd)
    }
}

