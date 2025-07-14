package com.chargepoint.session.sessionprocessor.domain


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DecisionRepository : JpaRepository<Decision, Long>