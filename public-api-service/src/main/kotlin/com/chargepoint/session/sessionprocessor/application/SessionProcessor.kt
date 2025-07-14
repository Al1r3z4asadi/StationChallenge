package com.chargepoint.session.sessionprocessor.application
import com.chargepoint.session.messagecontract.commands.Command
import com.chargepoint.session.messagecontract.commands.StartSessionCommand
import com.chargepoint.session.messagecontract.events.SessionDecisionMadeEvent
import com.chargepoint.session.sessionprocessor.domain.Decision
import com.chargepoint.session.sessionprocessor.domain.DecisionRepository
import com.chargepoint.session.sessionprocessor.externalservice.auth.AuthServiceAdapter
import com.chargepoint.session.sessionprocessor.externalservice.callback.CallbackRequest
import com.chargepoint.session.sessionprocessor.externalservice.callback.CallbackService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SessionProcessor (
    private val authService: AuthServiceAdapter,
    private val decisionRepo: DecisionRepository,
    private val eventPublisher: ApplicationEventPublisher
){
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun process(cmd: Command) {
        when (cmd) {
            is StartSessionCommand -> processStartSession(cmd)
            else -> logger.warn("Received unhandled command type: ${cmd::class.simpleName}")
        }
    }

    private fun processStartSession(cmd: StartSessionCommand) {
        val authResp = authService.authenticate(cmd.driverToken)

        val finalStatus = when {
            false -> "unknown"
            authResp?.valid == true -> "allowed"
            else -> "not_allowed"
        }
        logger.info("Decision for station ${cmd.stationId} is: $finalStatus")
        val decision = Decision(
            stationId   = cmd.stationId.toString(),
            driverToken = cmd.driverToken,
            status      = finalStatus
        )
        val event = SessionDecisionMadeEvent(
            stationId = cmd.stationId,
            driverToken = cmd.driverToken,
            status = decision.status,
            callbackUrl = cmd.callbackUrl,
            causationId = cmd.correlationId
        )
        eventPublisher.publishEvent(event)
    }
}