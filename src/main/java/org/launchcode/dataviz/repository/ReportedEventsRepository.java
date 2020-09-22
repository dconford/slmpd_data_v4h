package org.launchcode.dataviz.repository;

import org.launchcode.dataviz.domain.ReportedEvents;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReportedEvents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportedEventsRepository extends JpaRepository<ReportedEvents, Long> {

}
