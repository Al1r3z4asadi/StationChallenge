package com.chargepoint.session.api.controller

import com.chargepoint.session.api.request.StartSessionRequest
import com.chargepoint.session.common.MessageDispatcher
import com.chargepoint.session.messagecontract.commands.StartSessionCommand
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@Validated
@RequestMapping("/charging-sessions")
class ChargingSessionController(
    private val dispatcher: MessageDispatcher // Injected via constructor
) {
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun startSession(@RequestBody @Valid req: StartSessionRequest): Map<String, String> {
        val cmd = StartSessionCommand(
            stationId = req.stationId,
            driverToken = req.driverToken,
            callbackUrl = URI.create(req.callbackUrl)
        )
        dispatcher.dispatch(cmd)
        return mapOf(
            "status" to "accepted",
            "message" to "Request is being processed asynchronously. The result will be sent to the provided callback URL."
        )
    }
}