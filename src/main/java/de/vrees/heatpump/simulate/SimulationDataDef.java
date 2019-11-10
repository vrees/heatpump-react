package de.vrees.heatpump.simulate;

import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.statemachine.Events;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Data
@Slf4j
public class SimulationDataDef {

    private Processdata processdata;

    private int numberOfRepetitions;

    private List<Events> eventsToSend;
}
