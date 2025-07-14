package com.chargepoint.session.sessionprocessor.externalservice.callback

import com.chargepoint.session.messagecontract.events.SessionDecisionMadeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CallbackEventListener(private val callbackService: CallbackService) {

    @EventListener
    fun handleDecisionEvent(event: SessionDecisionMadeEvent) {
        val cbReq = CallbackRequest(
            station_id = event.stationId,
            driver_token = event.driverToken,
            status = event.status
        )
        callbackService.sendDecision(event.callbackUrl, cbReq)
    }
}