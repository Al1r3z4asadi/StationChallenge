package com.chargepoint.session.common

import com.chargepoint.session.messagecontract.Message

interface MessageDispatcher {
    fun <T : Message> dispatch(message: T)
}
