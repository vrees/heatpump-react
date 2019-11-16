package de.vrees.heatpump.coolant;


import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

/**
 * Diese Klasse bildet den Druck/Temperatur -Verlauf des Kuehlmittels R134a in einem Polynom 4-ter Ordnung ab.
 *
 * Im Niederdruck-Bereich von -10 bis +10 Grad Celsius ist die Abweichung kleiner als 0.02 Grad.
 *
 * Created by vrees on 13.02.16.
 */
public class InterpolationR134a {

//    double pressureBar[] = {1.421, 3.126, 6.694, 12.259};
//    double temperature[] = {-5, 10, 30, 50};

    double pressureBar[] = {0.997, 1.421, 1.913, 2.478, 3.126};
    double temperature[] = {-10, -5, -0, 5, 10,};


    private PolynomialFunctionNewtonForm polynom;

    public InterpolationR134a() {

        DividedDifferenceInterpolator divider = new DividedDifferenceInterpolator();
        polynom = divider.interpolate(pressureBar, temperature);

    }

    /**
     * Berechnet zu dem gegeben Druck in bar die entsprechende Sättiungstemperarur des Kühlmittels R134a
     *
     * Beispiel 5°C entspricht 2.478 bar (über Normaldruck)
     *
     * @param druck in Bar relativ zum Normadruck
     * @return die Temperatur in Grad Celsius
     */
    public double calcTemperatur(double druck) {
        return polynom.value(druck);
    }

    public PolynomialFunctionNewtonForm getPolynom() {
        return polynom;
    }

}
