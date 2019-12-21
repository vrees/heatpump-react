package de.vrees.heatpump.slaves.beckhoff;

import us.ihmc.etherCAT.master.Slave;
import us.ihmc.etherCAT.master.SyncManager;
import us.ihmc.etherCAT.master.TxPDO;

import java.util.StringJoiner;

/**
 * EL1008 | 8-Kanal-Digital-Eingangsklemme 24 V DC, 3 ms
 *
 * https://www.beckhoff.de/default.asp?ethercat/el1008.htm
 *
 */
public class EL1008 extends Slave {
    private static final long vendorID = 0x00000002L;
    private static final long productCode = 0x03f03052L;


    public class Input extends TxPDO {
        protected Input(int address) {
            super(address);
        }

        Bool input = new Bool();
    }

    private final Input userConfirmation = new Input(0x1a00);
    private final Input heatRequest = new Input(0x1a01);
    private final Input alarmExpansionValve = new Input(0x1a02);
    private final Input operatingStateWaterPump = new Input(0x1a03);
    private final Input incidentCompressor = new Input(0x1a04);
    private final Input incidentFlow = new Input(0x1a05);
    private final Input incidentHighPressure = new Input(0x1a06);
    private final Input incidentLowPressure = new Input(0x1a07);

    public EL1008(int aliasAddress, int configAddress) {
        super(vendorID, productCode, aliasAddress, configAddress);

        registerSyncManager(new SyncManager(0, false));

        sm(0).registerPDO(userConfirmation);
        sm(0).registerPDO(heatRequest);
        sm(0).registerPDO(alarmExpansionValve);
        sm(0).registerPDO(operatingStateWaterPump);
        sm(0).registerPDO(operatingStateWaterPump);
        sm(0).registerPDO(incidentFlow);
        sm(0).registerPDO(incidentHighPressure);
        sm(0).registerPDO(incidentLowPressure);
    }

    // Ein/Aus-Quittierung - Button
    public boolean isUserConfirmation() {
        return userConfirmation.input.get();
    }

    // Wärme-Anforderung
    public boolean isHeatRequest() {
        return heatRequest.input.get();
    }

    public boolean isAlarmExpansionValve() {
        return alarmExpansionValve.input.get();
    }

    // Heizungspumpe ein/aus
    public boolean isOperatingStateWaterPump() {
        return operatingStateWaterPump.input.get();
    }

    // Störung Verdichter / Motorschutzschalter
    public boolean isIncidentCompressor() {
        return incidentCompressor.input.get();
    }

    // Stoerung Durchfluss - minimale Druchlussmenge unterschritten
    public boolean isIncidentFlow() {
        return incidentFlow.input.get();
    }

    // Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck
    public boolean isIncidentHighPressure() {
        return incidentHighPressure.input.get();
    }

    // Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck
    public boolean isIncidentLowPressure() {
        return incidentLowPressure.input.get();
    }

    public String toProcessdataString() {
        return new StringJoiner(", ", EL1008.class.getSimpleName() + "[", "]")
                .add("userConfirmation=" + isUserConfirmation())
                .add("heatRequest=" + isHeatRequest())
                .add("alarmExpansionValve=" + isAlarmExpansionValve())
                .add("operatingStateWaterPump=" + isOperatingStateWaterPump())
                .add("incidentCompressor=" + isIncidentCompressor())
                .add("incidentFlow=" + isIncidentFlow())
                .add("incidentHighPressure=" + isIncidentHighPressure())
                .add("incidentLowPressure=" + isIncidentLowPressure())
                .toString();
    }

}
