package com.chargepoint.session.application


import com.chargepoint.session.messagecontract.commands.StartSessionCommand
import com.chargepoint.session.messagecontract.events.SessionDecisionMadeEvent
import com.chargepoint.session.sessionprocessor.application.SessionProcessor
import com.chargepoint.session.sessionprocessor.domain.Decision
import com.chargepoint.session.sessionprocessor.domain.DecisionRepository
import com.chargepoint.session.sessionprocessor.externalservice.auth.AuthServiceAdapter
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.context.ApplicationEventPublisher
import java.net.URI
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class SessionProcessorTest {

    @Mock
    private lateinit var authService: AuthServiceAdapter
    @Mock
    private lateinit var decisionRepo: DecisionRepository
    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    @InjectMocks
    private lateinit var sessionProcessor: SessionProcessor

    @Test
    fun `given driver is allowed, should save 'allowed' status and publish event`() {
        // Arrange
        val command = createStartSessionCommand()

        whenever(authService.authenticate(command.driverToken)).thenReturn("allowed")

        // Act
        sessionProcessor.process(command)

        // Assert
        val decisionCaptor = argumentCaptor<Decision>()
        verify(decisionRepo).save(decisionCaptor.capture())
        assert(decisionCaptor.firstValue.status == "allowed")
        verify(eventPublisher).publishEvent(any<SessionDecisionMadeEvent>())
    }

    @Test
    fun `given driver is not allowed, should save 'not_allowed' status and publish event`() {
        // Arrange
        val command = createStartSessionCommand()

        whenever(authService.authenticate(command.driverToken)).thenReturn("not_allowed")

        // Act
        sessionProcessor.process(command)

        // Assert
        val decisionCaptor = argumentCaptor<Decision>()
        verify(decisionRepo).save(decisionCaptor.capture())
        assert(decisionCaptor.firstValue.status == "not_allowed")
        verify(eventPublisher).publishEvent(any<SessionDecisionMadeEvent>())
    }

    @Test
    fun `given auth service fails, should save 'unknown' status and publish event`() {
        // Arrange
        val command = createStartSessionCommand()
        whenever(authService.authenticate(command.driverToken)).thenReturn("unknown")

        // Act
        sessionProcessor.process(command)

        // Assert
        val decisionCaptor = argumentCaptor<Decision>()
        verify(decisionRepo).save(decisionCaptor.capture())
        assert(decisionCaptor.firstValue.status == "unknown")
        verify(eventPublisher).publishEvent(any<SessionDecisionMadeEvent>())
    }

    private fun createStartSessionCommand(): StartSessionCommand {
        return StartSessionCommand(
            stationId = UUID.randomUUID(),
            driverToken = "a-valid-test-driver-token-123",
            callbackUrl = URI.create("https://example.com/callback")
        )
    }
}
