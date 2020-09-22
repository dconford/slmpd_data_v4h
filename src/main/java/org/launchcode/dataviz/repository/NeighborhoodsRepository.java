package org.launchcode.dataviz.repository;

import org.launchcode.dataviz.domain.Neighborhoods;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Neighborhoods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NeighborhoodsRepository extends JpaRepository<Neighborhoods, Long> {

}
