package de.vrees.heatpump.simulate;

import de.vrees.heatpump.domain.Processdata;


public class ProcessdataConstants {

    public static final Processdata PD_DEFAULT = initializeDefault();

    private static Processdata initializeDefault()
    {
        Processdata pd = new Processdata();
        pd.setTemperatureEvaporatingIn(6.4f);
        pd.setTemperatureEvaporatingOut(4.2f);
        pd.setTemperatureFlow(45.8f);
        pd.setTemperatureReturn(38.7f);
        pd.setTemperatureSwitchOnSensor(42.5f);
        pd.setTemperatureOverheatedGas(6.1f);

        pd.pressureHigh(11.2f);
        pd.pressureLow(3.4f);
        pd.pressureDiffenceEvaporator(97f);

        pd.setHeatRequest(false);
        pd.setUserConfirmation(false);
        pd.setAlarmExpansionValve(false);
        pd.setIncidentFlow(false);
        pd.setIncidentCompressor(false);
        pd.setIncidentLowPressure(false);
        pd.setIncidentHighPressure(false);
        pd.setOperatingStateWaterPump(false);
        pd.setOperatingStateCompressor(false);

        pd.setCalculatedOverheatTemperature(0.7f);
        pd.setWarningLowPressure(false);
        pd.setWarningHighPressure(false);

        return pd;
    }

}
