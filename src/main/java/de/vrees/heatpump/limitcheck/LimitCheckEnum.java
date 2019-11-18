package de.vrees.heatpump.limitcheck;

import static de.vrees.heatpump.limitcheck.FailureLevel.*;

public enum LimitCheckEnum {

    // Prüfe maximalen Druckverlust des Verdampfer-Wärmetauschers [mbar]
    PRESSURE_DIFF(ERROR, "Druckdifferenz Verdampfer zu hoch."),

    // Prüfe maximalen Druck auf der Niederdruckseite [bar]
    LOW_PRESSURE_WARN(WARNING,"Sensor: Niederdruck zu niedrig."),

    // Prüfe maximalen Druck auf der Niederdruckseite über Ranco Thermostat
    LOW_PRESSURE_ERR(ERROR, "Thermostat: Niederdruck zu niedrig."),

    // Prüfe maximalen Druck auf der Hochdruckseite [bar]
    HIGH_PRESSURE_WARN(WARNING,"Sensor: Hochdruck zu zu hoch."),

    // Prüfe maximalen Druck auf der Hochdruckseite über Ranco Thermostat
    HIGH_PRESSURE_ERR(ERROR, "Thermostat: Hochdruck zu hoch."),

    // Prüfe minimale Temperatur am Austritt des Verdampfer-Wärmetausschers. Triplepunkt des Wassers. [°C]
    EVAPORATING_TEMPERATURE(WARNING,"Temperatur am Verdampferausgang zu niedrig."),

    // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
    ENSURE_FLOW_VOLUMNE(WARNING, "Durchflussmenge zu gering."),

    MOTOR_PROTECTION_SWITH(ERROR, "Motorschutzschalter ausgelöst"),

    ALARM_EXPANSION_VALVE(ERROR, "Expansionsventil ausgelöst");


    private String errorMessage;

    private FailureLevel warnLevel;

    LimitCheckEnum(FailureLevel warnLevel, String errorMessage) {
        this.warnLevel = warnLevel;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public FailureLevel getWarnLevel() {
        return warnLevel;
    }
}
