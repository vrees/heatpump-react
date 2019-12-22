package de.vrees.heatpump.simulate;


import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.slaves.beckhoff.*;

import java.time.Instant;

public class EcatToProcessdataMapper {


    public Processdata map(EL3122 el3122, EL2008 el2008, EL3204_1 el3204_1, EL3064 eL3064, EL1008 el1008, EL3204_2 el3204_2) {
        Processdata processdata = new Processdata();
//        processdata.setState( in.getState() );
        processdata.setTimestamp(Instant.now());
        processdata.setId(  processdata.getTimestamp().getEpochSecond());
        processdata.setTemperatureEvaporatingIn(el3204_1.getTemperatureEvaporatingIn());
        processdata.setTemperatureEvaporatingOut(el3204_1.getTemperatureEvaporatingOut());
        processdata.setTemperatureFlow(el3204_1.getTemperatureFlow());
        processdata.setTemperatureReturn(el3204_1.getTemperatureReturn());
        processdata.setTemperatureSwitchOnSensor(el3204_2.getTemperatureSwitchOnSensor());
        processdata.setTemperatureOverheatedGas(el3204_2.getTemperatureOverheatedGas());
        processdata.setPressureHigh(el3122.getPressureHigh());
        processdata.setPressureLow(el3122.getPressureLow());
        processdata.setPressureDiffenceEvaporator(eL3064.getPressureDiffenceEvaporator());
        processdata.setHeatRequest(el1008.isHeatRequest());
        processdata.setUserConfirmation(el1008.isUserConfirmation());
        processdata.setAlarmExpansionValve(el1008.isAlarmExpansionValve());
        processdata.setIncidentFlow(el1008.isIncidentFlow());
        processdata.setIncidentCompressor(el1008.isIncidentCompressor());
        processdata.setIncidentLowPressure(el1008.isIncidentLowPressure());
        processdata.setIncidentHighPressure(el1008.isIncidentHighPressure());
//        processdata.setOperatingStateWaterPump(el2008.isOperatingStateWaterPump());
//        processdata.setOperatingStateCompressor(el2008.isOperatingStateCompressor());
//        processdata.setCalculatedOverheatTemperature(in.getCalculatedOverheatTemperature());
//        processdata.setWarningLowPressure(in.isWarningLowPressure());
//        processdata.setWarningHighPressure(in.isWarningHighPressure());
//        processdata.setWaitCounter(in.getWaitCounter());

        return processdata;
    }
}
