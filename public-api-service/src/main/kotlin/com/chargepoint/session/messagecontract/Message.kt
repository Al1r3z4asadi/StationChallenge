package com.chargepoint.session.messagecontract

import java.time.Instant
import java.util.*

interface Message {
    val correlationId: UUID
    val causationId: UUID
    val timestamp: Instant
}