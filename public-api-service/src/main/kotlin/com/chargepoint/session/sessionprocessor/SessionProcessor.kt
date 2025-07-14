package com.chargepoint.session.sessionprocessor
import com.chargepoint.session.messagecontract.commands.Command
import com.chargepoint.session.messagecontract.commands.StartSessionCommand
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SessionProcessor (
    private val authService: AuthService,
    private val decisionRepo: DecisionRepository,
    private val callbackService: CallbackService
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
        decisionRepo.save(decision)
        val cbReq = CallbackRequest(
            station_id   = cmd.stationId,
            driver_token = cmd.driverToken,
            status       = finalStatus
        )
        callbackService.sendDecision(cmd.callbackUrl, cbReq)
    }
}