package com.chargepoint.auth.service

import java.util.*

interface AuthService {
    fun checkACL(driverToken: String, stationId: UUID): AuthorizationStatus
}
