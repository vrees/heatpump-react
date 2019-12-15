package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.domain.FailureMessage;
import de.vrees.heatpump.repository.FailureMessageRepository;
import de.vrees.heatpump.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link de.vrees.heatpump.domain.FailureMessage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FailureMessageResource {

    private final Logger log = LoggerFactory.getLogger(FailureMessageResource.class);

    private static final String ENTITY_NAME = "failureMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FailureMessageRepository failureMessageRepository;

    public FailureMessageResource(FailureMessageRepository failureMessageRepository) {
        this.failureMessageRepository = failureMessageRepository;
    }

    /**
     * {@code POST  /failure-messages} : Create a new failureMessage.
     *
     * @param failureMessage the failureMessage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new failureMessage, or with status {@code 400 (Bad Request)} if the failureMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/failure-messages")
    public ResponseEntity<FailureMessage> createFailureMessage(@RequestBody FailureMessage failureMessage) throws URISyntaxException {
        log.debug("REST request to save FailureMessage : {}", failureMessage);
        if (failureMessage.getId() != null) {
            throw new BadRequestAlertException("A new failureMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FailureMessage result = failureMessageRepository.save(failureMessage);
        return ResponseEntity.created(new URI("/api/failure-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /failure-messages} : Updates an existing failureMessage.
     *
     * @param failureMessage the failureMessage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated failureMessage,
     * or with status {@code 400 (Bad Request)} if the failureMessage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the failureMessage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/failure-messages")
    public ResponseEntity<FailureMessage> updateFailureMessage(@RequestBody FailureMessage failureMessage) throws URISyntaxException {
        log.debug("REST request to update FailureMessage : {}", failureMessage);
        if (failureMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FailureMessage result = failureMessageRepository.save(failureMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, failureMessage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /failure-messages} : get all the failureMessages.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of failureMessages in body.
     */
    @GetMapping("/failure-messages")
    public List<FailureMessage> getAllFailureMessages() {
        log.debug("REST request to get all FailureMessages");
        return failureMessageRepository.findAll();
    }

    /**
     * {@code GET  /failure-messages/:id} : get the "id" failureMessage.
     *
     * @param id the id of the failureMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the failureMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/failure-messages/{id}")
    public ResponseEntity<FailureMessage> getFailureMessage(@PathVariable Long id) {
        log.debug("REST request to get FailureMessage : {}", id);
        Optional<FailureMessage> failureMessage = failureMessageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(failureMessage);
    }

    /**
     * {@code DELETE  /failure-messages/:id} : delete the "id" failureMessage.
     *
     * @param id the id of the failureMessage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/failure-messages/{id}")
    public ResponseEntity<Void> deleteFailureMessage(@PathVariable Long id) {
        log.debug("REST request to delete FailureMessage : {}", id);
        failureMessageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
