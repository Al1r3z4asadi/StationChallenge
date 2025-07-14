package com.chargepoint.session.sessionprocessor


import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "session_decisions")
data class Decision(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val stationId: String,

    @Column(nullable = false)
    val driverToken: String,

    @Column(nullable = false)
    val status: String,

    @Column(nullable = false)
    val decidedAt: Instant = Instant.now()
)
