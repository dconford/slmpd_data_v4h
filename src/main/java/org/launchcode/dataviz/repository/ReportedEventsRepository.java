package org.launchcode.dataviz.repository;

import org.launchcode.dataviz.domain.ReportedEvents;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Data  repository for the ReportedEvents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportedEventsRepository extends JpaRepository<ReportedEvents, Long> {

    /**
     * /counts - count all records in reported events.
     */
    long count();

    /**
     * /counts/categories - count all records by crime category.
     * @return
     */
    @Query(value = "select crime_categories_id, count(*) from reported_events group by crime_categories_id", nativeQuery = true)
    List<ArrayList<Long>> countsByCrimeCategories();

    /**
     *
     * /counts/categories/neighborhoods - count all records in a neighborhood by crime category.
     * @param neighborhoodCode
     * @return
     */
    @Query(value = "select crime_categories_id, count(*) from reported_events where neighborhood_code = ? group by crime_categories_id", nativeQuery = true)
    List<ArrayList<Long>> countsByCrimeCategoriesBySingleNeighborhood(Integer neighborhoodCode);


    /**
     *
     * /counts/categories/neighborhoodsbydate - count all records in a neighborhood and date by crime category.
     * @param neighborhoodCode, codedDates
     * @return
     */
    @Query(value = "select crime_categories_id, count(*) from reported_events where neighborhood_code = ?1 and coded_dates_id = ?2 group by crime_categories_id", nativeQuery = true)
    List<ArrayList<Long>> countsByCrimeCategoriesBySingleNeighborhoodAndDate(Integer neighborhoodCode, Integer codedDates);



}
