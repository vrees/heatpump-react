package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.repository.ProcessdataRepository;
import de.vrees.heatpump.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link de.vrees.heatpump.domain.Processdata}.
 */
@RestController
@RequestMapping("/api")
public class ProcessdataResource {

    private final Logger log = LoggerFactory.getLogger(ProcessdataResource.class);

    private static final String ENTITY_NAME = "processdata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessdataRepository processdataRepository;

    public ProcessdataResource(ProcessdataRepository processdataRepository) {
        this.processdataRepository = processdataRepository;
    }

    /**
     * {@code POST  /processdata} : Create a new processdata.
     *
     * @param processdata the processdata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processdata, or with status {@code 400 (Bad Request)} if the processdata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processdata")
    public ResponseEntity<Processdata> createProcessdata(@RequestBody Processdata processdata) throws URISyntaxException {
        log.debug("REST request to save Processdata : {}", processdata);
        if (processdata.getId() != null) {
            throw new BadRequestAlertException("A new processdata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Processdata result = processdataRepository.save(processdata);
        return ResponseEntity.created(new URI("/api/processdata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processdata} : Updates an existing processdata.
     *
     * @param processdata the processdata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processdata,
     * or with status {@code 400 (Bad Request)} if the processdata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processdata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processdata")
    public ResponseEntity<Processdata> updateProcessdata(@RequestBody Processdata processdata) throws URISyntaxException {
        log.debug("REST request to update Processdata : {}", processdata);
        if (processdata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Processdata result = processdataRepository.save(processdata);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processdata.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /processdata} : get all the processdata.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processdata in body.
     */
    @GetMapping("/processdata")
    public List<Processdata> getAllProcessdata() {
        log.debug("REST request to get all Processdata");
        return processdataRepository.findAll();
    }

    /**
     * {@code GET  /processdata/:id} : get the "id" processdata.
     *
     * @param id the id of the processdata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processdata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processdata/{id}")
    public ResponseEntity<Processdata> getProcessdata(@PathVariable String id) {
        log.debug("REST request to get Processdata : {}", id);
        Optional<Processdata> processdata = processdataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(processdata);
    }

    /**
     * {@code DELETE  /processdata/:id} : delete the "id" processdata.
     *
     * @param id the id of the processdata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processdata/{id}")
    public ResponseEntity<Void> deleteProcessdata(@PathVariable String id) {
        log.debug("REST request to delete Processdata : {}", id);
        processdataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
