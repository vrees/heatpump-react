package de.vrees.heatpump.statemachine.guards;

import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.ExtendedStateKeys;
import de.vrees.heatpump.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.util.List;

//@RequiredArgsConstructor
//@Service
@Slf4j
public class GuardSwitchOn implements Guard<States, Events> {

    @Autowired
    private LimitChecker limitChecker;

    @Override
    public boolean evaluate(StateContext<States, Events> context) {
        Processdata processdata = context.getExtendedState().get(ExtendedStateKeys.PROCESS_DATA, Processdata.class);

        // execute all checks
        boolean limitChecks = checkLimits(processdata).size() == 0;

        // ensure minimal Switch off duration
        boolean waitCounter = checkWaitCounter(processdata);

        log.debug("GuardSwitchOn - limitChecks: {}, waitCounter: {}", limitChecks, waitCounter);

        return limitChecks && waitCounter;
    }

    private List<LimitCheckResult> checkLimits(Processdata processdata) {
        return limitChecker.validate(processdata);
    }

    private boolean checkWaitCounter(Processdata processdata) {
        return processdata.getWaitCounter() == null || processdata.getWaitCounter() > 0;
    }
}
