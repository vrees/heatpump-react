package de.vrees.heatpump.limitcheck;

public enum LimitCheckEnum {

    // Prüfe maximalen Druckverlust des Verdampfer-Wärmetauschers [mbar]
    PRESSURE_DIFF("Druckdifferenz Verdampfer zu hoch."),

    // Prüfe maximalen Druck auf der Niederdruckseite [bar]
    LOW_PRESSURE_WARN("Sensor: Niederdruck zu niedrig."),

    // Prüfe maximalen Druck auf der Niederdruckseite über Ranco Thermostat
    LOW_PRESSURE_ERR("Thermostat: Niederdruck zu niedrig."),

    // Prüfe maximalen Druck auf der Hochdruckseite [bar]
    HIGH_PRESSURE_WARN("Sensor: Hochdruck zu zu hoch."),

    // Prüfe maximalen Druck auf der Hochdruckseite über Ranco Thermostat
    HIGH_PRESSURE_ERR("Thermostat: Hochdruck zu hoch."),

    // Prüfe minimale Temperatur am Austritt des Verdampfer-Wärmetausschers. Triplepunkt des Wassers. [°C]
    EVAPORATING_TEMPERATURE("Temperatur am Verdampferausgang zu niedrig."),

    // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
    ENSURE_FLOW_VOLUMNE("Durchflussmenge zu gering."),

    MOTOR_PROTECTION_SWITH("Motorschutzschalter ausgelöst"),

    ALARM_EXPANSION_VALVE("Expansionsventil ausgelöst");


    private String errorMessage;

    LimitCheckEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
