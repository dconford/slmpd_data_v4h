package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.domain.CodedDates;
import org.launchcode.dataviz.repository.CodedDatesRepository;
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
 * REST controller for managing {@link org.launchcode.dataviz.domain.CodedDates}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CodedDatesResource {

    private final Logger log = LoggerFactory.getLogger(CodedDatesResource.class);

    private static final String ENTITY_NAME = "codedDates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodedDatesRepository codedDatesRepository;

    public CodedDatesResource(CodedDatesRepository codedDatesRepository) {
        this.codedDatesRepository = codedDatesRepository;
    }

    /**
     * {@code POST  /coded-dates} : Create a new codedDates.
     *
     * @param codedDates the codedDates to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codedDates, or with status {@code 400 (Bad Request)} if the codedDates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coded-dates")
    public ResponseEntity<CodedDates> createCodedDates(@RequestBody CodedDates codedDates) throws URISyntaxException {
        log.debug("REST request to save CodedDates : {}", codedDates);
        if (codedDates.getId() != null) {
            throw new BadRequestAlertException("A new codedDates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodedDates result = codedDatesRepository.save(codedDates);
        return ResponseEntity.created(new URI("/api/coded-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coded-dates} : Updates an existing codedDates.
     *
     * @param codedDates the codedDates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codedDates,
     * or with status {@code 400 (Bad Request)} if the codedDates is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codedDates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coded-dates")
    public ResponseEntity<CodedDates> updateCodedDates(@RequestBody CodedDates codedDates) throws URISyntaxException {
        log.debug("REST request to update CodedDates : {}", codedDates);
        if (codedDates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodedDates result = codedDatesRepository.save(codedDates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, codedDates.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coded-dates} : get all the codedDates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codedDates in body.
     */
    @GetMapping("/coded-dates")
    public List<CodedDates> getAllCodedDates() {
        log.debug("REST request to get all CodedDates");
        return codedDatesRepository.findAll();
    }

    /**
     * {@code GET  /coded-dates/:id} : get the "id" codedDates.
     *
     * @param id the id of the codedDates to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codedDates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coded-dates/{id}")
    public ResponseEntity<CodedDates> getCodedDates(@PathVariable Long id) {
        log.debug("REST request to get CodedDates : {}", id);
        Optional<CodedDates> codedDates = codedDatesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(codedDates);
    }

    /**
     * {@code DELETE  /coded-dates/:id} : delete the "id" codedDates.
     *
     * @param id the id of the codedDates to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coded-dates/{id}")
    public ResponseEntity<Void> deleteCodedDates(@PathVariable Long id) {
        log.debug("REST request to delete CodedDates : {}", id);
        codedDatesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
