package de.vrees.heatpump.statemachine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StateMachineEventListener extends StateMachineListenerAdapter<States, Events> {

    private StateContext<States, Events> stateContext;

    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        log.info("Statemachine: State changed from {} to {}", from != null ? from.getId() : ".", to.getId());
        stateContext.getExtendedState().getVariables().put(ExtendedStateKeys.TICK_COUNTER, 0L);
    }

    @Override
    public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
        super.stateMachineStarted(stateMachine);
        stateContext.getExtendedState().getVariables().put(ExtendedStateKeys.TICK_COUNTER, 0L);
    }

    @Override
    public void stateContext(StateContext<States, Events> stateContext) {
        this.stateContext = stateContext;
    }
}
