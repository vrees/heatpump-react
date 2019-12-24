package de.vrees.heatpump.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Heatpump.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */

@Data
@ConfigurationProperties(prefix = "application.config", ignoreUnknownFields = false)
public class ApplicationProperties {
    private float heatTemperaturSwitchOn; // Einschalttemperatur bei deren Unterschreiten die Waermepumpe eingeschaltet wird
    private float heatTemperaturSwitchOff; // Einschalttemperatur bei deren Ueberschreiten die Waermepume ausgeschaltet wird
    private long minIdleSec; // Minimale Auschalt-Dauer (verhindert haeufiges Einschalten) [Sekunden]
    private int lowPressureIgnoreTimeSec; // Nach dem Einschalten sollen Niederdruck-Warnungen für eine gewisse Zeit ignoriert werden
    private float temperatureDiffBacklashOff; //  Temperaturdifferenz zwischen Vorlauf und Rücklauf, bei dessen Unterschreiten die Wasserpumpe ausgeschaltet wird
}
