package de.vrees.heatpump.statemachine.actions;

import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.ExtendedStateKeys;
import de.vrees.heatpump.statemachine.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.time.Instant;

public class StoreSwitchOffTimeAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> context) {
        context.getExtendedState().getVariables().put(ExtendedStateKeys.SWITCH_OFF_TIME, Instant.now());
    }
}
