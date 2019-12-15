package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.repository.ProcessdataRepository;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.ExtendedStateKeys;
import de.vrees.heatpump.statemachine.States;
import de.vrees.heatpump.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.vrees.heatpump.domain.Processdata}.
 */
@RestController
@RequestMapping("/api")
@Slf4j
@Transactional
public class ProcessdataResource {

    private static final String ENTITY_NAME = "processdata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessdataRepository processdataRepository;

    private final StateMachine<States, Events> stateMachine;


    public ProcessdataResource(ProcessdataRepository processdataRepository, StateMachine<States, Events> stateMachine) {
        this.processdataRepository = processdataRepository;
        this.stateMachine = stateMachine;
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

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processdata in body.
     */
    @GetMapping("/processdata")
    public ResponseEntity<List<Processdata>> getAllProcessdata(Pageable pageable) {
        log.debug("REST request to get a page of Processdata");
        Page<Processdata> page = processdataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processdata/:id} : get the "id" processdata.
     *
     * @param id the id of the processdata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processdata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processdata/{id}")
    public ResponseEntity<Processdata> getProcessdata(@PathVariable Long id) {
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
    public ResponseEntity<Void> deleteProcessdata(@PathVariable Long id) {
        log.debug("REST request to delete Processdata : {}", id);
        processdataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/processdata/latest")
    public ResponseEntity<Processdata> getLatestProcessdata() {
        Processdata processdata = stateMachine.getExtendedState().get(ExtendedStateKeys.PROCESS_DATA, Processdata.class);
        log.debug("REST request to get latest Processdata: {}", processdata);
        return ResponseEntity.ok().body(processdata);
    }
}
