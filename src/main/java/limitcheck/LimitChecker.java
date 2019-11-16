package limitcheck;

import de.vrees.heatpump.domain.Processdata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LimitChecker {


    public List<LimitCheckEnum> validate(Processdata pd) {
        ArrayList<LimitCheckEnum> failedChecks = new ArrayList<>();

        if (pd.getPressureDiffenceEvaporator() > )

/*

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

                MOTOR_PROTECTION_SWITH("Motorschutzschalter ausgelöst");
*/


        return failedChecks;
    }
}
