package de.vrees.heatpump.simulate;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import de.vrees.heatpump.web.websocket.WebsocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

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

    private int indexOfDefRecord = 0;
    private int indexInsideDefRecord = -1;

    private long counter = 0;

    @Scheduled(fixedRate = 10)
    public void scheduleTask() {
        counter++;
        SimulationDataDef definition = next();

        if (definition.getNumberOfRepetitions() == indexInsideDefRecord) {
            definition.getEventsToSend().forEach(e -> stateMachine.sendEvent(e));
        }

        if (counter % 200 == 0) {
            Processdata nextVal = modifyNexProcesdata(processdataMapper.map(definition.getProcessdata()));
            websocketService.sendProcessdata(nextVal);
        }
    }

    private Processdata modifyNexProcesdata(Processdata processdata) {
        processdata.setTimestamp(Instant.now());
        processdata.setId(processdata.getTimestamp().toString());
        processdata.setTemperatureFlow(random(37, 50, 10));
        processdata.setTemperatureReturn(random(30, 45, 10));
        processdata.setTemperatureEvaporatingIn(random(5, 7, 10));
        processdata.setTemperatureEvaporatingOut(random(4, 5, 10));
        processdata.setTemperatureOverheatedGas(random(7, 8, 10));
        processdata.setTemperatureSwitchOnSensor(random(42, 44, 10));
        processdata.setPressureLow(random(2, 4, 10));
        processdata.setPressureHigh(random(8, 11, 10));
        processdata.setPressureDiffenceEvaporator(random(10, 200, 10));

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
