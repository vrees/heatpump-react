package de.vrees.heatpump.simulate;


import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

import static de.vrees.heatpump.simulate.ProcessdataConstants.SIMULATION_DATA;


@Component
@RequiredArgsConstructor
@Profile("simulate")
@Slf4j
public class SimulateMaster implements Iterator<SimulationDataDef> {

    private static final List<SimulationDataDef> simulationData = SIMULATION_DATA;

    private final StateMachine<States, Events> stateMachine;

    private final ProcessdataMapper processdataMapper;

    private int indexOfDefRecord = 0;
    private int indexInsideDefRecord = -1;


    @Scheduled(fixedRate = 100)
    public void scheduleTask() {
        SimulationDataDef definition = next();

        if (definition.getNumberOfRepetitions() == indexInsideDefRecord) {
            definition.getEventsToSend().forEach(e -> stateMachine.sendEvent(e));
        }

//        Processdata obj = processdataMapper.map(definition.getProcessdata());
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
}
