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


    public List<LimitCheckResult> validate(Processdata pd) {
        ArrayList<LimitCheckResult> failedChecks = new ArrayList<>();

        // Maximaler Druckverlust des Verdampfer-Wärmetauschers [mbar]
        if (pd.getPressureDiffenceEvaporator() > props.getPressureDiffenceEvaporatorMax())
            failedChecks.add(new LimitCheckResult(PRESSURE_DIFF, pd.getPressureDiffenceEvaporator().toString()));

        // Prüfe maximalen Druck auf der Niederdruckseite [bar]
        if (pd.getPressureLow() > props.getPressureLowMax())
            failedChecks.add(new LimitCheckResult(LOW_PRESSURE_WARN, pd.getPressureLow() .toString()));

        // Prüfe maximalen Druck auf der Niederdruckseite über Ranco Thermoste [bar]
        if (pd.isIncidentLowPressure())
            failedChecks.add(new LimitCheckResult(LOW_PRESSURE_ERR, null));

        // Prüfe maximalen Druck auf der Hochdruckseite [bar]
        if (pd.getPressureHigh() > props.getPressureHighMax())
            failedChecks.add(new LimitCheckResult(HIGH_PRESSURE_WARN, pd.getPressureHigh().toString()));

        // Prüfe maximalen Druck auf der Hochdruckseite über Ranco Thermoste [bar]
        if (pd.isIncidentHighPressure())
            failedChecks.add(new LimitCheckResult(HIGH_PRESSURE_ERR, null));

        // Prüfe minimale Temperatur am Austritt des Verdampfer-Wärmetausschers. Triplepunkt des Wassers. [°C]
        if (pd.getTemperatureEvaporatingOut() < props.getEvaporatingTemperatureOutMin())
            failedChecks.add(new LimitCheckResult(EVAPORATING_TEMPERATURE, pd.getTemperatureEvaporatingOut().toString()));

        // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
        if (pd.isIncidentFlow())
            failedChecks.add(new LimitCheckResult(ENSURE_FLOW_VOLUMNE, null));

        // Prüfe, of Wasser Durchflussmenge ausreichend ist. Mindestens 2500 Liter/Stunde
        if (pd.isIncidentCompressor())
            failedChecks.add(new LimitCheckResult(MOTOR_PROTECTION_SWITH, null));

        // Expansionsvetil meldet Fehler
        if (pd.isAlarmExpansionValve())
            failedChecks.add(new LimitCheckResult(ALARM_EXPANSION_VALVE, null));

        return failedChecks;
    }
}
