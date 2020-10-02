package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.domain.ReportedEvents;
import org.launchcode.dataviz.repository.ReportedEventsRepository;
import org.launchcode.dataviz.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.launchcode.dataviz.domain.ReportedEvents}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReportedEventsResource {

    private final Logger log = LoggerFactory.getLogger(ReportedEventsResource.class);

    private static final String ENTITY_NAME = "reportedEvents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportedEventsRepository reportedEventsRepository;

    public ReportedEventsResource(ReportedEventsRepository reportedEventsRepository) {
        this.reportedEventsRepository = reportedEventsRepository;
    }

    /**
     * {@code POST  /reported-events} : Create a new reportedEvents.
     *
     * @param reportedEvents the reportedEvents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportedEvents, or with status {@code 400 (Bad Request)} if the reportedEvents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reported-events")
    public ResponseEntity<ReportedEvents> createReportedEvents(@RequestBody ReportedEvents reportedEvents) throws URISyntaxException {
        log.debug("REST request to save ReportedEvents : {}", reportedEvents);
        if (reportedEvents.getId() != null) {
            throw new BadRequestAlertException("A new reportedEvents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportedEvents result = reportedEventsRepository.save(reportedEvents);
        return ResponseEntity.created(new URI("/api/reported-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reported-events} : Updates an existing reportedEvents.
     *
     * @param reportedEvents the reportedEvents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportedEvents,
     * or with status {@code 400 (Bad Request)} if the reportedEvents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportedEvents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reported-events")
    public ResponseEntity<ReportedEvents> updateReportedEvents(@RequestBody ReportedEvents reportedEvents) throws URISyntaxException {
        log.debug("REST request to update ReportedEvents : {}", reportedEvents);
        if (reportedEvents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReportedEvents result = reportedEventsRepository.save(reportedEvents);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportedEvents.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reported-events} : get all the reportedEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportedEvents in body.
     */
    @GetMapping("/reported-events")
    public ResponseEntity<List<ReportedEvents>> getAllReportedEvents(Pageable pageable) {
        log.debug("REST request to get a page of ReportedEvents");
        Page<ReportedEvents> page = reportedEventsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reported-events/:id} : get the "id" reportedEvents.
     *
     * @param id the id of the reportedEvents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportedEvents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reported-events/{id}")
    public ResponseEntity<ReportedEvents> getReportedEvents(@PathVariable Long id) {
        log.debug("REST request to get ReportedEvents : {}", id);
        Optional<ReportedEvents> reportedEvents = reportedEventsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reportedEvents);
    }

    /**
     * {@code GET  /reported-events/counts} : get count of reportedEvents.
     *
     * @return the {count} with status {@code 200 (OK)} and the count of reportedEvents in body.
     */
    @GetMapping("reported-events/counts")
    public Long count() {
        Long count = reportedEventsRepository.count();
        return count;
    }

    /**
     * {@code GET  /reported-events/counts/categories} : get count of reportedEvents, ordered by crimeCategories.
     *
     * @return the {resultbucket} with status {@code 200 (OK)} and the count of reportedEvents, ordered by
     *  crimeCategories in body.
     */
    @GetMapping("reported-events/counts/categories")
    public List<ArrayList<Long>>countsByCrimeCategories() {
        List<ArrayList<Long>> resultbucket = reportedEventsRepository.countsByCrimeCategories();
        return resultbucket;
    }

    /**
     * {@code GET  /reported-events/counts/categories/neighborhoods} : get count of reportedEvents in a single
     * neighborhood, ordered by crimeCategories.
     *
     * @param neighborhoodCode
     * @return the {resultbucket} with status {@code 200 (OK)} and the count of reportedEvents in a single neighborhood,
     * ordered by crimeCategories in body.
     */
    @GetMapping("reported-events/counts/categories/neighborhoods")
    public List<ArrayList<Long>> countsByCrimeCategoriesBySingleNeighborhood(Integer neighborhoodCode) {
        List<ArrayList<Long>> resultbucket = reportedEventsRepository.countsByCrimeCategoriesBySingleNeighborhood(neighborhoodCode);
        return resultbucket;
    }

    /**
     *
     * @param neighborhoodCode
     * @param codedDates
     * @return
     */
    @GetMapping("reported-events/counts/categories/neighborhoodsbydate")
    public List<ArrayList<Long>> countsByCrimeCategoriesBySingleNeighborhoodAndDat(Integer neighborhoodCode, Integer codedDates) {
        List<ArrayList<Long>> resultbucket = reportedEventsRepository.countsByCrimeCategoriesBySingleNeighborhoodAndDate(neighborhoodCode, codedDates);
        return resultbucket;
    }




    /**
     * {@code DELETE  /reported-events/:id} : delete the "id" reportedEvents.
     *
     * @param id the id of the reportedEvents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reported-events/{id}")
    public ResponseEntity<Void> deleteReportedEvents(@PathVariable Long id) {
        log.debug("REST request to delete ReportedEvents : {}", id);
        reportedEventsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
