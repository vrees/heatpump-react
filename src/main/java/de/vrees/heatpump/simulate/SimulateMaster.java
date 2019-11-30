package de.vrees.heatpump.simulate;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.FailureMessage;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.statemachine.*;
import de.vrees.heatpump.web.websocket.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static de.vrees.heatpump.simulate.ProcessdataConstants.SIMULATION_DATA;


@Component
@RequiredArgsConstructor
@Profile("simulate")
@Slf4j
public class SimulateMaster implements Iterator<SimulationDataDef> {

    private static final List<SimulationDataDef> simulationData = SIMULATION_DATA;

    private final StateMachine<States, Events> stateMachine;
    private final ProcessdataMapper processdataMapper;
    private final WebsocketService websocketService;
    private final LimitChecker limitChecker;

    private int indexOfDefRecord = 0;
    private int indexInsideDefRecord = -1;

    private long countLoops = 0;

    @Scheduled(fixedRate = 100)
    public void scheduleTask() {
        SimulationDataDef definition = next();
        Processdata processdata = modifyNexProcesdata(processdataMapper.map(definition.getProcessdata()));

        List<LimitCheckResult> faildedChecks = checkLimits(processdata);
        processOutgoingValues(processdata, faildedChecks);
        storeProcessdataInStatemachine(processdata);

        if (definition.getNumberOfRepetitions() == indexInsideDefRecord) {
            definition.getEventsToSend().forEach(e -> stateMachine.sendEvent(e));
        }

        sendData(processdata);
    }

    private void sendData(Processdata processdata) {
        String reason = stateMachine.getExtendedState().get(ExtendedStateKeys.IMMEDIATE_SEND_DATA, String.class);

        if(!StringUtils.isEmpty(reason)) {
            stateMachine.getExtendedState().getVariables().remove(ExtendedStateKeys.IMMEDIATE_SEND_DATA);
            countLoops =0;
            log.debug("Data sent immediate. Reason={}", reason);
        }

        if (countLoops % 20 == 0) {
            websocketService.sendProcessdata(processdata);
        }
        countLoops++;
    }

    private void processOutgoingValues(Processdata processdata, List<LimitCheckResult> faildedChecks) {
        States state = stateMachine.getState().getId();

        processdata.setOperatingStateCompressor(stateMachine.getExtendedState().get(ExtendedStateKeys.COMPRESSOR_STATE, Boolean.class));
        processdata.setOperatingStateWaterPump(stateMachine.getExtendedState().get(ExtendedStateKeys.WATERPUMP_STATE, Boolean.class));

        faildedChecks.forEach(check -> {
            processdata.getMessages().add(new FailureMessage(check.getLimitCheck().getWarnLevel(), check.getLimitCheck().getErrorMessage(), check.getValue()));
        });

//        processdata.getMessages().add(new FailureMessage(FailureLevel.ERROR, "Thermostat: Niederdruck zu niedrig.", "22.3"));
//        processdata.getMessages().add(new FailureMessage(FailureLevel.WARNING, "Durchflussmenge zu gering.", null));

        processdata.setState(state);

    }

    private void storeProcessdataInStatemachine(Processdata processdata) {
        stateMachine.getExtendedState().getVariables().put(ExtendedStateKeys.PROCESS_DATA, processdata);
    }

    private List<LimitCheckResult> checkLimits(Processdata processdata) {
        List<LimitCheckResult> failedChecks = limitChecker.validate(processdata);

        if (failedChecks.size() > 0) {
            stateMachine.sendEvent(
                MessageBuilder
                    .withPayload(Events.LIMIT_EXCEEDED)
                    .setHeader(EventHeaderEnum.FAILED_CHECKS.name(), failedChecks).build()
            );
            stateMachine.getExtendedState().getVariables().put(ExtendedStateKeys.IMMEDIATE_SEND_DATA, "checks failed");
        }

        return failedChecks;
    }

    private Processdata modifyNexProcesdata(Processdata processdata) {
        processdata.setTimestamp(Instant.now());
        processdata.setId(processdata.getTimestamp().toString());
        processdata.setTemperatureFlow(random(37, 45, 10));
        processdata.setTemperatureReturn(random(36, 42, 10));
        processdata.setTemperatureEvaporatingIn(random(5, 7, 10));
        processdata.setTemperatureEvaporatingOut(random(4, 5, 10));
        processdata.setTemperatureOverheatedGas(random(7, 8, 10));
        processdata.setTemperatureSwitchOnSensor(random(42, 44, 10));
        processdata.setPressureLow(random(3, 4, 10));
        processdata.setPressureHigh(random(8, 11, 10));
        processdata.setPressureDiffenceEvaporator(random(10, 101, 10));

        return processdata;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public SimulationDataDef next() {
        SimulationDataDef dataDef = simulationData.get(indexOfDefRecord);

        if (indexInsideDefRecord < dataDef.getNumberOfRepetitions()) {
            indexInsideDefRecord++;
            return dataDef;
        } else {
            indexInsideDefRecord = -1;
            indexOfDefRecord++;
            if (indexOfDefRecord >= simulationData.size()) {
                indexOfDefRecord = 0;
            }
            return next();
        }
    }

    private float random(int min, int max, int scaleFactor) {
        float val = ThreadLocalRandom.current().nextFloat();
        return min + round(val * (max - min), scaleFactor);
    }

    public static float round(float number, int scaleFactor) {
        float tmp = number * scaleFactor;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / scaleFactor;
    }
}
