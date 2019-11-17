package de.vrees.heatpump.statemachine.actions;

import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.ExtendedStateKeys;
import de.vrees.heatpump.statemachine.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class SwitchAllOffAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> context) {
        context.getExtendedState().getVariables().put(ExtendedStateKeys.COMPRESSOR_STATE, false);
        context.getExtendedState().getVariables().put(ExtendedStateKeys.PUMP_STATE, false);
    }
}
