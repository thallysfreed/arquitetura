package com.poc.web.rest;

import com.poc.domain.NormaTecnica;
import com.poc.repository.NormaTecnicaRepository;
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
 * REST controller for managing {@link com.poc.domain.NormaTecnica}.
 */
@RestController
@RequestMapping("/api")
public class NormaTecnicaResource {

    private final Logger log = LoggerFactory.getLogger(NormaTecnicaResource.class);

    private static final String ENTITY_NAME = "normaTecnica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormaTecnicaRepository normaTecnicaRepository;

    public NormaTecnicaResource(NormaTecnicaRepository normaTecnicaRepository) {
        this.normaTecnicaRepository = normaTecnicaRepository;
    }

    /**
     * {@code POST  /norma-tecnicas} : Create a new normaTecnica.
     *
     * @param normaTecnica the normaTecnica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new normaTecnica, or with status {@code 400 (Bad Request)} if the normaTecnica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/norma-tecnicas")
    public ResponseEntity<NormaTecnica> createNormaTecnica(@RequestBody NormaTecnica normaTecnica) throws URISyntaxException {
        log.debug("REST request to save NormaTecnica : {}", normaTecnica);
        if (normaTecnica.getId() != null) {
            throw new BadRequestAlertException("A new normaTecnica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NormaTecnica result = normaTecnicaRepository.save(normaTecnica);
        return ResponseEntity.created(new URI("/api/norma-tecnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /norma-tecnicas} : Updates an existing normaTecnica.
     *
     * @param normaTecnica the normaTecnica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normaTecnica,
     * or with status {@code 400 (Bad Request)} if the normaTecnica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the normaTecnica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/norma-tecnicas")
    public ResponseEntity<NormaTecnica> updateNormaTecnica(@RequestBody NormaTecnica normaTecnica) throws URISyntaxException {
        log.debug("REST request to update NormaTecnica : {}", normaTecnica);
        if (normaTecnica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NormaTecnica result = normaTecnicaRepository.save(normaTecnica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, normaTecnica.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /norma-tecnicas} : get all the normaTecnicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normaTecnicas in body.
     */
    @GetMapping("/norma-tecnicas")
    public List<NormaTecnica> getAllNormaTecnicas() {
        log.debug("REST request to get all NormaTecnicas");
        return normaTecnicaRepository.findAll();
    }

    /**
     * {@code GET  /norma-tecnicas/:id} : get the "id" normaTecnica.
     *
     * @param id the id of the normaTecnica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the normaTecnica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/norma-tecnicas/{id}")
    public ResponseEntity<NormaTecnica> getNormaTecnica(@PathVariable Long id) {
        log.debug("REST request to get NormaTecnica : {}", id);
        Optional<NormaTecnica> normaTecnica = normaTecnicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(normaTecnica);
    }

    /**
     * {@code DELETE  /norma-tecnicas/:id} : delete the "id" normaTecnica.
     *
     * @param id the id of the normaTecnica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/norma-tecnicas/{id}")
    public ResponseEntity<Void> deleteNormaTecnica(@PathVariable Long id) {
        log.debug("REST request to delete NormaTecnica : {}", id);
        normaTecnicaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
