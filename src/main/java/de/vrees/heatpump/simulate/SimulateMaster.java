package de.vrees.heatpump.simulate;


import com.google.common.collect.Lists;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
@Profile("simulate")
@Slf4j
public class SimulateMaster implements Iterator<SimulationDataDef> {

    private StateMachine<States, Events> stateMachine;

    private List<SimulationDataDef> simulationData = new ArrayList();

    private int indexOfDefRecord = 0;
    private int indexInsideDefRecord = -1;

    public SimulateMaster(StateMachine<States, Events> sm) {
        this.stateMachine = sm;
        simulationData.add(new SimulationDataDef(ProcessdataConstants.PD_DEFAULT, 20, Lists.newArrayList(Events.SWITCH_ON), null));
        simulationData.add(new SimulationDataDef(ProcessdataConstants.PD_DEFAULT, 100, Lists.newArrayList(Events.HEAT_REQUEST), null));
        simulationData.add(new SimulationDataDef(ProcessdataConstants.PD_DEFAULT, 70, Lists.newArrayList(Events.TEMPERATURE_REACHED), null));
        simulationData.add(new SimulationDataDef(ProcessdataConstants.PD_DEFAULT, 10, Lists.newArrayList(Events.COOLDED_DOWN), null));
    }

    @Scheduled(fixedRate = 100)
    public void scheduleTask() {
        SimulationDataDef definition = next();

        if (definition.getNumberOfRepetitions() == indexInsideDefRecord) {
            definition.getEventsToSend().forEach(e -> stateMachine.sendEvent(e));
        }

//        log.debug("Scheduler fired! indexOfDefRecord={}, indexInsideDefRecord={}", indexOfDefRecord, indexInsideDefRecord);
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
