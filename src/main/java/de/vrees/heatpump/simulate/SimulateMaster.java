package de.vrees.heatpump.simulate;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.limitcheck.LimitCheckResult;
import de.vrees.heatpump.limitcheck.LimitChecker;
import de.vrees.heatpump.web.websocket.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static de.vrees.heatpump.simulate.ProcessdataConstants.SIMULATION_DATA_SIMPLE;


@Component
@RequiredArgsConstructor
@Profile("simulate")
@Slf4j
public class SimulateMaster implements Iterator<SimulationDataDef> {

    private static final List<SimulationDataDef> simulationData = SIMULATION_DATA_SIMPLE;

    private final de.vrees.heatpump.statemachine.StateMachineWrapper stateMachineWrapper;
    private final ProcessdataMapper processdataMapper;
    private final WebsocketService websocketService;
    private final LimitChecker limitChecker;

    private int indexOfDefRecord = 0;
    private int indexInsideDefRecord = -1;

    private long countLoops = 0;

    @Scheduled(fixedRate = 50)
    public void scheduleTask() {
        SimulationDataDef definition = next();
        Processdata processdata = modifyNexProcesdata(processdataMapper.map(definition.getProcessdata()));
        stateMachineWrapper.storeAndPreProcess(processdata);

        List<LimitCheckResult> faildedChecks = stateMachineWrapper.checkLimits(processdata);
        stateMachineWrapper.processOutgoingValues(processdata, faildedChecks);

        if (definition.getNumberOfRepetitions() == indexInsideDefRecord) {
            definition.getEventsToSend().forEach(e -> stateMachineWrapper.sendEvent(e));
        }

        stateMachineWrapper.sendData(processdata);
    }

    private Processdata modifyNexProcesdata(Processdata processdata) {
        processdata.setTimestamp(Instant.now());
        processdata.setId(ThreadLocalRandom.current().nextLong());
        processdata.setTemperatureFlow(random(37, 45.2f, 10));
        processdata.setTemperatureReturn(random(36, 43, 10));
        processdata.setTemperatureEvaporatingIn(random(5, 7, 10));
        processdata.setTemperatureEvaporatingOut(random(4, 5, 10));
        processdata.setTemperatureOverheatedGas(random(7, 8, 10));
        processdata.setTemperatureSwitchOnSensor(random(41.8f, 44f, 10));
        processdata.setPressureLow(random(3, 4, 10));
        processdata.setPressureHigh(random(8, 11, 10));
        processdata.setPressureDiffenceEvaporator(random(10, 99, 10));

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

    private float random(float min, float max, int scaleFactor) {
        float val = ThreadLocalRandom.current().nextFloat();
        return min + round(val * (max - min), scaleFactor);
    }

    public static float round(float number, int scaleFactor) {
        float tmp = number * scaleFactor;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / scaleFactor;
    }
}
