package org.launchcode.dataviz.repository;

import org.launchcode.dataviz.domain.CrimeCategories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CrimeCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrimeCategoriesRepository extends JpaRepository<CrimeCategories, Long> {

}
