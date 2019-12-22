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
@ConfigurationProperties(prefix = "application.limits", ignoreUnknownFields = false)
public class LimitProperties {
    private float pressureHighMax;  // Maximaler Druck auf der Hochdruckseite [bar]
    private float pressureLowMax; // Maximaler Druck auf der Niederdruckseite [bar]
    private float pressureDiffenceEvaporatorMax; // Maximaler Druckverlust des Verdampfer-Wärmetauschers [mbar]
    private float evaporatingTemperatureOutMin; // Minimale Temperatur am Austritt des Verdampfer-Wärmetausschers. Triplepunkt des Wassers. [�C]
}
