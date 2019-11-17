package de.vrees.heatpump.limitcheck;

import de.vrees.heatpump.config.ApplicationProperties;
import de.vrees.heatpump.domain.Processdata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static de.vrees.heatpump.limitcheck.LimitCheckEnum.*;

@RequiredArgsConstructor
@Service
public class LimitChecker {

    private final ApplicationProperties props;


    public List<LimitCheckEnum> validate(Processdata pd) {
        ArrayList<LimitCheckEnum> failedChecks = new ArrayList<>();

        // Maximaler Druckverlust des Verdampfer-Wärmetauschers [mbar]
        if (pd.getPressureDiffenceEvaporator() > props.getPressureDiffenceEvaporatorMax())
            failedChecks.add(PRESSURE_DIFF);

        // Prüfe maximalen Druck auf der Niederdruckseite [bar]
        if (pd.getPressureLow() > props.getPressureLowMax())
            failedChecks.add(LOW_PRESSURE_WARN);

        // Prüfe maximalen Druck auf der Niederdruckseite über Ranco Thermoste [bar]
        if (pd.isIncidentLowPressure())
            failedChecks.add(LOW_PRESSURE_ERR);

        // Prüfe maximalen Druck auf der Hochdruckseite [bar]
        if (pd.getPressureHigh() > props.getPressureHighMax())
            failedChecks.add(HIGH_PRESSURE_WARN);

        // Prüfe maximalen Druck auf der Hochdruckseite über Ranco Thermoste [bar]
        if (pd.isIncidentHighPressure())
            failedChecks.add(HIGH_PRESSURE_ERR);

        // Prüfe minimale Temperatur am Austritt des Verdampfer-Wärmetausschers. Triplepunkt des Wassers. [°C]
        if (pd.getTemperatureEvaporatingOut() < props.getEvaporatingTemperatureOutMin())
            failedChecks.add(EVAPORATING_TEMPERATURE);

        // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
        if (pd.isIncidentFlow())
            failedChecks.add(ENSURE_FLOW_VOLUMNE);

        // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
        if (pd.isIncidentCompressor())
            failedChecks.add(MOTOR_PROTECTION_SWITH);

        // Expansionsvetil meldet Fehler
        if (pd.isAlarmExpansionValve())
            failedChecks.add(ALARM_EXPANSION_VALVE);

        return failedChecks;
    }
}
