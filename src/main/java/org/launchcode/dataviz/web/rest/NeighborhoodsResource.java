package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.domain.Neighborhoods;
import org.launchcode.dataviz.repository.NeighborhoodsRepository;
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
 * REST controller for managing {@link org.launchcode.dataviz.domain.Neighborhoods}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NeighborhoodsResource {

    private final Logger log = LoggerFactory.getLogger(NeighborhoodsResource.class);

    private static final String ENTITY_NAME = "neighborhoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NeighborhoodsRepository neighborhoodsRepository;

    public NeighborhoodsResource(NeighborhoodsRepository neighborhoodsRepository) {
        this.neighborhoodsRepository = neighborhoodsRepository;
    }

    /**
     * {@code POST  /neighborhoods} : Create a new neighborhoods.
     *
     * @param neighborhoods the neighborhoods to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new neighborhoods, or with status {@code 400 (Bad Request)} if the neighborhoods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/neighborhoods")
    public ResponseEntity<Neighborhoods> createNeighborhoods(@RequestBody Neighborhoods neighborhoods) throws URISyntaxException {
        log.debug("REST request to save Neighborhoods : {}", neighborhoods);
        if (neighborhoods.getId() != null) {
            throw new BadRequestAlertException("A new neighborhoods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Neighborhoods result = neighborhoodsRepository.save(neighborhoods);
        return ResponseEntity.created(new URI("/api/neighborhoods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /neighborhoods} : Updates an existing neighborhoods.
     *
     * @param neighborhoods the neighborhoods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated neighborhoods,
     * or with status {@code 400 (Bad Request)} if the neighborhoods is not valid,
     * or with status {@code 500 (Internal Server Error)} if the neighborhoods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/neighborhoods")
    public ResponseEntity<Neighborhoods> updateNeighborhoods(@RequestBody Neighborhoods neighborhoods) throws URISyntaxException {
        log.debug("REST request to update Neighborhoods : {}", neighborhoods);
        if (neighborhoods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Neighborhoods result = neighborhoodsRepository.save(neighborhoods);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, neighborhoods.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /neighborhoods} : get all the neighborhoods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of neighborhoods in body.
     */
    @GetMapping("/neighborhoods")
    public List<Neighborhoods> getAllNeighborhoods() {
        log.debug("REST request to get all Neighborhoods");
        return neighborhoodsRepository.findAll();
    }

    /**
     * {@code GET  /neighborhoods/:id} : get the "id" neighborhoods.
     *
     * @param id the id of the neighborhoods to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the neighborhoods, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/neighborhoods/{id}")
    public ResponseEntity<Neighborhoods> getNeighborhoods(@PathVariable Long id) {
        log.debug("REST request to get Neighborhoods : {}", id);
        Optional<Neighborhoods> neighborhoods = neighborhoodsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(neighborhoods);
    }

    /**
     * {@code DELETE  /neighborhoods/:id} : delete the "id" neighborhoods.
     *
     * @param id the id of the neighborhoods to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/neighborhoods/{id}")
    public ResponseEntity<Void> deleteNeighborhoods(@PathVariable Long id) {
        log.debug("REST request to delete Neighborhoods : {}", id);
        neighborhoodsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
