package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.domain.CrimeCategories;
import org.launchcode.dataviz.repository.CrimeCategoriesRepository;
import org.launchcode.dataviz.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.launchcode.dataviz.domain.CrimeCategories}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CrimeCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CrimeCategoriesResource.class);

    private static final String ENTITY_NAME = "crimeCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrimeCategoriesRepository crimeCategoriesRepository;

    public CrimeCategoriesResource(CrimeCategoriesRepository crimeCategoriesRepository) {
        this.crimeCategoriesRepository = crimeCategoriesRepository;
    }

    /**
     * {@code POST  /crime-categories} : Create a new crimeCategories.
     *
     * @param crimeCategories the crimeCategories to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crimeCategories, or with status {@code 400 (Bad Request)} if the crimeCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crime-categories")
    public ResponseEntity<CrimeCategories> createCrimeCategories(@RequestBody CrimeCategories crimeCategories) throws URISyntaxException {
        log.debug("REST request to save CrimeCategories : {}", crimeCategories);
        if (crimeCategories.getId() != null) {
            throw new BadRequestAlertException("A new crimeCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrimeCategories result = crimeCategoriesRepository.save(crimeCategories);
        return ResponseEntity.created(new URI("/api/crime-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crime-categories} : Updates an existing crimeCategories.
     *
     * @param crimeCategories the crimeCategories to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crimeCategories,
     * or with status {@code 400 (Bad Request)} if the crimeCategories is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crimeCategories couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crime-categories")
    public ResponseEntity<CrimeCategories> updateCrimeCategories(@RequestBody CrimeCategories crimeCategories) throws URISyntaxException {
        log.debug("REST request to update CrimeCategories : {}", crimeCategories);
        if (crimeCategories.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CrimeCategories result = crimeCategoriesRepository.save(crimeCategories);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crimeCategories.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /crime-categories} : get all the crimeCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crimeCategories in body.
     */
    @GetMapping("/crime-categories")
    public List<CrimeCategories> getAllCrimeCategories() {
        log.debug("REST request to get all CrimeCategories");
        return crimeCategoriesRepository.findAll();
    }

    /**
     * {@code GET  /crime-categories/:id} : get the "id" crimeCategories.
     *
     * @param id the id of the crimeCategories to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crimeCategories, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crime-categories/{id}")
    public ResponseEntity<CrimeCategories> getCrimeCategories(@PathVariable Long id) {
        log.debug("REST request to get CrimeCategories : {}", id);
        Optional<CrimeCategories> crimeCategories = crimeCategoriesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(crimeCategories);
    }

    /**
     * {@code DELETE  /crime-categories/:id} : delete the "id" crimeCategories.
     *
     * @param id the id of the crimeCategories to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crime-categories/{id}")
    public ResponseEntity<Void> deleteCrimeCategories(@PathVariable Long id) {
        log.debug("REST request to delete CrimeCategories : {}", id);
        crimeCategoriesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
