package de.vrees.heatpump.statemachine;

import de.vrees.heatpump.config.ApplicationProperties;
import de.vrees.heatpump.domain.FailureMessage;
import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.slaves.beckhoff.EL2004;
import de.vrees.heatpump.slaves.beckhoff.EL2008;
import de.vrees.heatpump.web.websocket.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StateMachineWrapper {
    private final StateMachine<States, Events> stateMachine;
    private final WebsocketService websocketService;
    private final LimitChecker limitChecker;
    private final ApplicationProperties props;

//    private long countLoops = 0;


    public List<LimitCheckResult> checkLimits(Processdata processdata) {
        List<LimitCheckResult> failedChecks = limitChecker.validate(processdata);

        if (failedChecks.size() > 0) {
            stateMachine.sendEvent(
                MessageBuilder
                    .withPayload(Events.LIMIT_EXCEEDED)
                    .setHeader(EventHeaderEnum.FAILED_CHECKS.name(), failedChecks).build()
            );
//            stateMachine.getExtendedState().getVariables().put(ExtendedStateKeys.TICK_COUNTER, 0L);
        }

        return failedChecks;
    }

    public void sendData(Processdata processdata) {
        Long tickCounter = stateMachine.getExtendedState().get(ExtendedStateKeys.TICK_COUNTER, Long.class);

        if (tickCounter % 40 == 0) {
            websocketService.sendProcessdata(processdata);
            logValues(processdata);
        }

        tickCounter++;
        stateMachine.getExtendedState().getVariables().put(ExtendedStateKeys.TICK_COUNTER, tickCounter);
    }

    private void logValues(Processdata processdata) {
        log.debug(processdata.toString());
    }

    public void storeAndPreProcess(Processdata processdata) {
        stateMachine.getExtendedState().getVariables().put(ExtendedStateKeys.PROCESS_DATA, processdata);

        // calc WaitCounter
        Instant timestamp = stateMachine.getExtendedState().get(ExtendedStateKeys.SWITCH_OFF_TIME, Instant.class);
        if (timestamp != null) {
            long diff = Instant.now().getEpochSecond() -
                timestamp.plus(Duration.ofSeconds(props.getMinIdleSec())).getEpochSecond();
            processdata.setWaitCounter((int) diff);
        }

        generateEvents(processdata);
    }

    private void generateEvents(Processdata processdata) {
        switch (stateMachine.getState().getId()) {
            case READY:
                if (processdata.getTemperatureSwitchOnSensor() < props.getHeatTemperaturSwitchOn()) {
                    log.debug("TemperatureSwitchOnSensor < HeatTemperaturSwitchOn: {} < {}", processdata.getTemperatureSwitchOnSensor(), props.getHeatTemperaturSwitchOn());
                    stateMachine.sendEvent(Events.HEAT_REQUEST);
                }
                break;
            case RUNNING:
                if (processdata.getTemperatureFlow() > props.getHeatTemperaturSwitchOff()) {
                    log.debug("TemperatureFlow > HeatTemperaturSwitchOff: {} > {}", processdata.getTemperatureFlow(), props.getHeatTemperaturSwitchOff());
                    stateMachine.sendEvent(Events.SWITCH_OFF);
                }
                break;
            case BACKLASH:
                if ((processdata.getTemperatureFlow() - processdata.getTemperatureReturn()) < props.getTemperatureDiffBacklashOff()) {
                    log.debug("(TemperatureFlow() - TemperatureReturn) < TemperatureDiffBacklashOff(): ({} - {}) < {}", processdata.getTemperatureFlow(), processdata.getTemperatureReturn(), props.getTemperatureDiffBacklashOff());
                    stateMachine.sendEvent(Events.COOLDED_DOWN);
                }
                break;
        }
    }

    public void processOutgoingValues(Processdata processdata, List<LimitCheckResult> faildedChecks) {
        States state = stateMachine.getState().getId();

        processdata.setOperatingStateCompressor(stateMachine.getExtendedState().get(ExtendedStateKeys.COMPRESSOR_STATE, Boolean.class));
        processdata.setOperatingStateWaterPump(stateMachine.getExtendedState().get(ExtendedStateKeys.WATERPUMP_STATE, Boolean.class));

        faildedChecks.forEach(check -> {
            processdata.getMessages().add(new FailureMessage(check.getLimitCheck().getWarnLevel(), check.getLimitCheck().getErrorMessage(), check.getValue()));
        });

        processdata.setState(state);
    }

    public void writeOutgoingValuesToEcatBus(EL2004 el2004) {
        el2004.setOperatingStateCompressor(stateMachine.getExtendedState().get(ExtendedStateKeys.COMPRESSOR_STATE, Boolean.class));
        el2004.setOperatingStateWaterPump(stateMachine.getExtendedState().get(ExtendedStateKeys.WATERPUMP_STATE, Boolean.class));
    }

    public void writeOutgoingValuesToEcatBus(EL2008 el2008) {
        el2008.setOperatingStateCompressor(stateMachine.getExtendedState().get(ExtendedStateKeys.COMPRESSOR_STATE, Boolean.class));
        el2008.setOperatingStateWaterPump(stateMachine.getExtendedState().get(ExtendedStateKeys.WATERPUMP_STATE, Boolean.class));
    }

    public boolean sendEvent(Events e) {
        return stateMachine.sendEvent(e);
    }
}
