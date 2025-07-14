package com.chargepoint.session.sessionprocessor.externalservice.callback

import java.util.*

data class CallbackRequest(
    val station_id: UUID,
    val driver_token: String,
    val status: String
)