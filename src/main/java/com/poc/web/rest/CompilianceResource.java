package com.poc.web.rest;

import com.poc.domain.Compiliance;
import com.poc.repository.CompilianceRepository;
import com.poc.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.poc.domain.Compiliance}.
 */
@RestController
@RequestMapping("/api")
public class CompilianceResource {

    private final Logger log = LoggerFactory.getLogger(CompilianceResource.class);

    private static final String ENTITY_NAME = "compiliance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompilianceRepository compilianceRepository;

    public CompilianceResource(CompilianceRepository compilianceRepository) {
        this.compilianceRepository = compilianceRepository;
    }

    /**
     * {@code POST  /compiliances} : Create a new compiliance.
     *
     * @param compiliance the compiliance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compiliance, or with status {@code 400 (Bad Request)} if the compiliance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compiliances")
    public ResponseEntity<Compiliance> createCompiliance(@RequestBody Compiliance compiliance) throws URISyntaxException {
        log.debug("REST request to save Compiliance : {}", compiliance);
        if (compiliance.getId() != null) {
            throw new BadRequestAlertException("A new compiliance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compiliance result = compilianceRepository.save(compiliance);
        return ResponseEntity.created(new URI("/api/compiliances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compiliances} : Updates an existing compiliance.
     *
     * @param compiliance the compiliance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compiliance,
     * or with status {@code 400 (Bad Request)} if the compiliance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compiliance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compiliances")
    public ResponseEntity<Compiliance> updateCompiliance(@RequestBody Compiliance compiliance) throws URISyntaxException {
        log.debug("REST request to update Compiliance : {}", compiliance);
        if (compiliance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Compiliance result = compilianceRepository.save(compiliance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, compiliance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compiliances} : get all the compiliances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compiliances in body.
     */
    @GetMapping("/compiliances")
    public List<Compiliance> getAllCompiliances() {
        log.debug("REST request to get all Compiliances");
        return compilianceRepository.findAll();
    }

    /**
     * {@code GET  /compiliances/:id} : get the "id" compiliance.
     *
     * @param id the id of the compiliance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compiliance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compiliances/{id}")
    public ResponseEntity<Compiliance> getCompiliance(@PathVariable Long id) {
        log.debug("REST request to get Compiliance : {}", id);
        Optional<Compiliance> compiliance = compilianceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(compiliance);
    }

    /**
     * {@code DELETE  /compiliances/:id} : delete the "id" compiliance.
     *
     * @param id the id of the compiliance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compiliances/{id}")
    public ResponseEntity<Void> deleteCompiliance(@PathVariable Long id) {
        log.debug("REST request to delete Compiliance : {}", id);
        compilianceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
