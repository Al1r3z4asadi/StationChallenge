package com.chargepoint.session.messagecontract.commands


import java.net.URI
import java.time.Instant
import java.util.UUID

data class StartSessionCommand(
    val stationId: UUID,
    val driverToken: String,
    val callbackUrl: URI,
    override val correlationId: UUID = UUID.randomUUID(),
    override val causationId: UUID = correlationId,
    override val timestamp: Instant = Instant.now()
) : Command
