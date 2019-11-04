package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import de.vrees.heatpump.web.rest.errors.BadRequestAlertException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class EventController {

    private StateMachine<States, Events> stateMachine;

    @PutMapping("/event/{strEvent}")
    public ResponseEntity<String> updateProcessdata(@PathVariable String strEvent, @RequestBody Object requestData) throws URISyntaxException {
        log.debug("REST request to send an event: {}, requestData={}", strEvent, requestData);
        Events event = Events.valueOf(strEvent);
        if (event == null) {
            throw new BadRequestAlertException("Invalid event", strEvent, "invalid");
        }

       boolean accepted = stateMachine.sendEvent(event);

        States currentState = stateMachine.getState().getId();

        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, processdata.getId().toString()))
            .body(currentState.name());
    }
}
