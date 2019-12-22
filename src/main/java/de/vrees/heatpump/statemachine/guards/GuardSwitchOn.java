package de.vrees.heatpump.statemachine.guards;

import de.vrees.heatpump.config.ApplicationProperties;
import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.ExtendedStateKeys;
import de.vrees.heatpump.statemachine.States;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GuardSwitchOn implements Guard<States, Events> {

    private final LimitChecker limitChecker;

    private final ApplicationProperties props;

    @Override
    public boolean evaluate(StateContext<States, Events> context) {

        // execute all checks
        if (checkLimits(context).size() > 0) return false;

        // ensure minimal Switch off duration
        return checkWaitCounter(context);
    }

    private List<LimitCheckResult> checkLimits(StateContext<States, Events> context) {
        Processdata processdata = context.getExtendedState().get(ExtendedStateKeys.PROCESS_DATA, Processdata.class);
        return limitChecker.validate(processdata);
    }

    private boolean checkWaitCounter(StateContext<States, Events> context) {
        Instant timestamp = context.getExtendedState().get(ExtendedStateKeys.SWITCH_OFF_TIME, Instant.class);

        return timestamp == null ||
            Instant.now().compareTo(timestamp.plus(Duration.ofSeconds(props.getMinIdleSec()))) >= 0;
    }
}
