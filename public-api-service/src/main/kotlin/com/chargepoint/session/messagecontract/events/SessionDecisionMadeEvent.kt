package com.chargepoint.session.messagecontract.events

import java.net.URI
import java.time.Instant
import java.util.*

data class SessionDecisionMadeEvent(
    val stationId: UUID,
    val driverToken: String,
    val status: String,
    val callbackUrl: URI,

    override val correlationId: UUID = UUID.randomUUID(),
    override val causationId: UUID,
    override val timestamp: Instant = Instant.now()
) : Event