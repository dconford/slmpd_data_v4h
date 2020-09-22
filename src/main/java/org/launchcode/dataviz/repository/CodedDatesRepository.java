package org.launchcode.dataviz.repository;

import org.launchcode.dataviz.domain.CodedDates;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CodedDates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodedDatesRepository extends JpaRepository<CodedDates, Long> {

}
