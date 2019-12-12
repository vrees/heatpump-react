package de.vrees.heatpump.domain;
import de.vrees.heatpump.domain.enumeration.States;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Processdata.
 */
@Entity
@Table(name = "processdata")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Processdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private Instant timestamp;

    /**
     * Verdampfungstemperatur in °C
     */
    @ApiModelProperty(value = "Verdampfungstemperatur in °C")
    @Column(name = "temperature_evaporating_in")
    private Float temperatureEvaporatingIn;

    /**
     * Verdampfungstemperatur out in °C
     */
    @ApiModelProperty(value = "Verdampfungstemperatur out in °C")
    @Column(name = "temperature_evaporating_out")
    private Float temperatureEvaporatingOut;

    /**
     * Vorlauf-Temperatur in °C
     */
    @ApiModelProperty(value = "Vorlauf-Temperatur in °C")
    @Column(name = "temperature_flow")
    private Float temperatureFlow;

    /**
     * Rücklauf-Temperatur in °C
     */
    @ApiModelProperty(value = "Rücklauf-Temperatur in °C")
    @Column(name = "temperature_return")
    private Float temperatureReturn;

    /**
     * SchaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur am Puffer oben in °C
     */
    @ApiModelProperty(value = "SchaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur am Puffer oben in °C")
    @Column(name = "temperature_switch_on_sensor")
    private Float temperatureSwitchOnSensor;

    /**
     * SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters, also auf der Niederdruck-Seite.\nWird zusammen mit dem Druck im Verdampfer zur Berechnung der Überhitzung benötigt\nGesättigteVerdampfungstemperatur = Druck mal TemperaturKonstante des Kühlmittels\nUeberhitzung = Temperatur des ueberhitzten Gases - GesättigteVerdampfungstemperatur
     */
    @ApiModelProperty(value = "SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters, also auf der Niederdruck-Seite.\nWird zusammen mit dem Druck im Verdampfer zur Berechnung der Überhitzung benötigt\nGesättigteVerdampfungstemperatur = Druck mal TemperaturKonstante des Kühlmittels\nUeberhitzung = Temperatur des ueberhitzten Gases - GesättigteVerdampfungstemperatur")
    @Column(name = "temperature_overheated_gas")
    private Float temperatureOverheatedGas;

    /**
     * Hochdruck Kältekreis in bar
     */
    @ApiModelProperty(value = "Hochdruck Kältekreis in bar")
    @Column(name = "pressure_high")
    private Float pressureHigh;

    /**
     * Niederdruck Kältekreis in bar
     */
    @ApiModelProperty(value = "Niederdruck Kältekreis in bar")
    @Column(name = "pressure_low")
    private Float pressureLow;

    /**
     * Druckdifferenz Verdampfer in mbar, minVal=0 maxVal=200
     */
    @ApiModelProperty(value = "Druckdifferenz Verdampfer in mbar, minVal=0 maxVal=200")
    @Column(name = "pressure_diffence_evaporator")
    private Float pressureDiffenceEvaporator;

    /**
     * Wärme Anforderung
     */
    @ApiModelProperty(value = "Wärme Anforderung")
    @Column(name = "heat_request")
    private Boolean heatRequest;

    /**
     * Taster Ein-/Aus- Quittierung
     */
    @ApiModelProperty(value = "Taster Ein-/Aus- Quittierung")
    @Column(name = "user_confirmation")
    private Boolean userConfirmation;

    /**
     * Alarm des Elektronischen Expansionsentils EEV
     */
    @ApiModelProperty(value = "Alarm des Elektronischen Expansionsentils EEV")
    @Column(name = "alarm_expansion_valve")
    private Boolean alarmExpansionValve;

    /**
     * Stoerung Durchfluss - minimale Druchlussmenge im Verdampfer unterschritten
     */
    @ApiModelProperty(value = "Stoerung Durchfluss - minimale Druchlussmenge im Verdampfer unterschritten")
    @Column(name = "incident_flow")
    private Boolean incidentFlow;

    /**
     * Stoerung Verdichter / Motorschutzschalter
     */
    @ApiModelProperty(value = "Stoerung Verdichter / Motorschutzschalter")
    @Column(name = "incident_compressor")
    private Boolean incidentCompressor;

    /**
     * Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck
     */
    @ApiModelProperty(value = "Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck")
    @Column(name = "incident_low_pressure")
    private Boolean incidentLowPressure;

    /**
     * Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck
     */
    @ApiModelProperty(value = "Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck")
    @Column(name = "incident_high_pressure")
    private Boolean incidentHighPressure;

    /**
     * Status Heizungspume: Ein/Aus
     */
    @ApiModelProperty(value = "Status Heizungspume: Ein/Aus")
    @Column(name = "operating_state_water_pump")
    private Boolean operatingStateWaterPump;

    /**
     * Status Verdichter: Ein/Aus
     */
    @ApiModelProperty(value = "Status Verdichter: Ein/Aus")
    @Column(name = "operating_state_compressor")
    private Boolean operatingStateCompressor;

    /**
     * Überhitzung:  Berechnet aus Kühlmittel-Temperatur am Ausgang des Verdampfers und dem Druck mal TemperaturKonstante des Kühlmittels
     */
    @ApiModelProperty(value = "Überhitzung:  Berechnet aus Kühlmittel-Temperatur am Ausgang des Verdampfers und dem Druck mal TemperaturKonstante des Kühlmittels")
    @Column(name = "calculated_overheat_temperature")
    private Float calculatedOverheatTemperature;

    /**
     * Warnung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)
     */
    @ApiModelProperty(value = "Warnung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)")
    @Column(name = "warning_low_pressure")
    private Boolean warningLowPressure;

    /**
     * Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)
     */
    @ApiModelProperty(value = "Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)")
    @Column(name = "warning_high_pressure")
    private Boolean warningHighPressure;

    /**
     * Status der Statemachine = Betriebszustand
     */
    @ApiModelProperty(value = "Status der Statemachine = Betriebszustand")
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private States state;

//    @ApiModelProperty(value = "Fehlermeldungen")
//    @Field("messages")
//    private List<FailureMessage> messages = new ArrayList();
//
//
//    @ApiModelProperty(value = "Anzahl Sekunden bis zum nächst möglichen Einschalten")
//    @Field("wait_counter")
//    private Integer waitCounter;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Processdata timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public States getState() {
        return state;
    }

    public Processdata state(States state) {
        this.state = state;
        return this;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Float getTemperatureEvaporatingIn() {
        return temperatureEvaporatingIn;
    }

    public Processdata temperatureEvaporatingIn(Float temperatureEvaporatingIn) {
        this.temperatureEvaporatingIn = temperatureEvaporatingIn;
        return this;
    }

    public void setTemperatureEvaporatingIn(Float temperatureEvaporatingIn) {
        this.temperatureEvaporatingIn = temperatureEvaporatingIn;
    }

    public Float getTemperatureEvaporatingOut() {
        return temperatureEvaporatingOut;
    }

    public Processdata temperatureEvaporatingOut(Float temperatureEvaporatingOut) {
        this.temperatureEvaporatingOut = temperatureEvaporatingOut;
        return this;
    }

    public void setTemperatureEvaporatingOut(Float temperatureEvaporatingOut) {
        this.temperatureEvaporatingOut = temperatureEvaporatingOut;
    }

    public Float getTemperatureFlow() {
        return temperatureFlow;
    }

    public Processdata temperatureFlow(Float temperatureFlow) {
        this.temperatureFlow = temperatureFlow;
        return this;
    }

    public void setTemperatureFlow(Float temperatureFlow) {
        this.temperatureFlow = temperatureFlow;
    }

    public Float getTemperatureReturn() {
        return temperatureReturn;
    }

    public Processdata temperatureReturn(Float temperatureReturn) {
        this.temperatureReturn = temperatureReturn;
        return this;
    }

    public void setTemperatureReturn(Float temperatureReturn) {
        this.temperatureReturn = temperatureReturn;
    }

    public Float getTemperatureSwitchOnSensor() {
        return temperatureSwitchOnSensor;
    }

    public Processdata temperatureSwitchOnSensor(Float temperatureSwitchOnSensor) {
        this.temperatureSwitchOnSensor = temperatureSwitchOnSensor;
        return this;
    }

    public void setTemperatureSwitchOnSensor(Float temperatureSwitchOnSensor) {
        this.temperatureSwitchOnSensor = temperatureSwitchOnSensor;
    }

    public Float getTemperatureOverheatedGas() {
        return temperatureOverheatedGas;
    }

    public Processdata temperatureOverheatedGas(Float temperatureOverheatedGas) {
        this.temperatureOverheatedGas = temperatureOverheatedGas;
        return this;
    }

    public void setTemperatureOverheatedGas(Float temperatureOverheatedGas) {
        this.temperatureOverheatedGas = temperatureOverheatedGas;
    }

    public Float getPressureHigh() {
        return pressureHigh;
    }

    public Processdata pressureHigh(Float pressureHigh) {
        this.pressureHigh = pressureHigh;
        return this;
    }

    public void setPressureHigh(Float pressureHigh) {
        this.pressureHigh = pressureHigh;
    }

    public Float getPressureLow() {
        return pressureLow;
    }

    public Processdata pressureLow(Float pressureLow) {
        this.pressureLow = pressureLow;
        return this;
    }

    public void setPressureLow(Float pressureLow) {
        this.pressureLow = pressureLow;
    }

    public Float getPressureDiffenceEvaporator() {
        return pressureDiffenceEvaporator;
    }

    public Processdata pressureDiffenceEvaporator(Float pressureDiffenceEvaporator) {
        this.pressureDiffenceEvaporator = pressureDiffenceEvaporator;
        return this;
    }

    public void setPressureDiffenceEvaporator(Float pressureDiffenceEvaporator) {
        this.pressureDiffenceEvaporator = pressureDiffenceEvaporator;
    }

    public Boolean isHeatRequest() {
        return heatRequest;
    }

    public Processdata heatRequest(Boolean heatRequest) {
        this.heatRequest = heatRequest;
        return this;
    }

    public void setHeatRequest(Boolean heatRequest) {
        this.heatRequest = heatRequest;
    }

    public Boolean isUserConfirmation() {
        return userConfirmation;
    }

    public Processdata userConfirmation(Boolean userConfirmation) {
        this.userConfirmation = userConfirmation;
        return this;
    }

    public void setUserConfirmation(Boolean userConfirmation) {
        this.userConfirmation = userConfirmation;
    }

    public Boolean isAlarmExpansionValve() {
        return alarmExpansionValve;
    }

    public Processdata alarmExpansionValve(Boolean alarmExpansionValve) {
        this.alarmExpansionValve = alarmExpansionValve;
        return this;
    }

    public void setAlarmExpansionValve(Boolean alarmExpansionValve) {
        this.alarmExpansionValve = alarmExpansionValve;
    }

    public Boolean isIncidentFlow() {
        return incidentFlow;
    }

    public Processdata incidentFlow(Boolean incidentFlow) {
        this.incidentFlow = incidentFlow;
        return this;
    }

    public void setIncidentFlow(Boolean incidentFlow) {
        this.incidentFlow = incidentFlow;
    }

    public Boolean isIncidentCompressor() {
        return incidentCompressor;
    }

    public Processdata incidentCompressor(Boolean incidentCompressor) {
        this.incidentCompressor = incidentCompressor;
        return this;
    }

    public void setIncidentCompressor(Boolean incidentCompressor) {
        this.incidentCompressor = incidentCompressor;
    }

    public Boolean isIncidentLowPressure() {
        return incidentLowPressure;
    }

    public Processdata incidentLowPressure(Boolean incidentLowPressure) {
        this.incidentLowPressure = incidentLowPressure;
        return this;
    }

    public void setIncidentLowPressure(Boolean incidentLowPressure) {
        this.incidentLowPressure = incidentLowPressure;
    }

    public Boolean isIncidentHighPressure() {
        return incidentHighPressure;
    }

    public Processdata incidentHighPressure(Boolean incidentHighPressure) {
        this.incidentHighPressure = incidentHighPressure;
        return this;
    }

    public void setIncidentHighPressure(Boolean incidentHighPressure) {
        this.incidentHighPressure = incidentHighPressure;
    }

    public Boolean isOperatingStateWaterPump() {
        return operatingStateWaterPump;
    }

    public Processdata operatingStateWaterPump(Boolean operatingStateWaterPump) {
        this.operatingStateWaterPump = operatingStateWaterPump;
        return this;
    }

    public void setOperatingStateWaterPump(Boolean operatingStateWaterPump) {
        this.operatingStateWaterPump = operatingStateWaterPump;
    }

    public Boolean isOperatingStateCompressor() {
        return operatingStateCompressor;
    }

    public Processdata operatingStateCompressor(Boolean operatingStateCompressor) {
        this.operatingStateCompressor = operatingStateCompressor;
        return this;
    }

    public void setOperatingStateCompressor(Boolean operatingStateCompressor) {
        this.operatingStateCompressor = operatingStateCompressor;
    }

    public Float getCalculatedOverheatTemperature() {
        return calculatedOverheatTemperature;
    }

    public Processdata calculatedOverheatTemperature(Float calculatedOverheatTemperature) {
        this.calculatedOverheatTemperature = calculatedOverheatTemperature;
        return this;
    }

    public void setCalculatedOverheatTemperature(Float calculatedOverheatTemperature) {
        this.calculatedOverheatTemperature = calculatedOverheatTemperature;
    }

    public Boolean isWarningLowPressure() {
        return warningLowPressure;
    }

    public Processdata warningLowPressure(Boolean warningLowPressure) {
        this.warningLowPressure = warningLowPressure;
        return this;
    }

    public void setWarningLowPressure(Boolean warningLowPressure) {
        this.warningLowPressure = warningLowPressure;
    }

    public Boolean isWarningHighPressure() {
        return warningHighPressure;
    }

    public Processdata warningHighPressure(Boolean warningHighPressure) {
        this.warningHighPressure = warningHighPressure;
        return this;
    }

    public void setWarningHighPressure(Boolean warningHighPressure) {
        this.warningHighPressure = warningHighPressure;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processdata)) {
            return false;
        }
        return id != null && id.equals(((Processdata) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Processdata{" +
            "id=" + getId() +
            ", timestamp='" + getTimestamp() + "'" +
            ", state='" + getState() + "'" +
            ", temperatureEvaporatingIn=" + getTemperatureEvaporatingIn() +
            ", temperatureEvaporatingOut=" + getTemperatureEvaporatingOut() +
            ", temperatureFlow=" + getTemperatureFlow() +
            ", temperatureReturn=" + getTemperatureReturn() +
            ", temperatureSwitchOnSensor=" + getTemperatureSwitchOnSensor() +
            ", temperatureOverheatedGas=" + getTemperatureOverheatedGas() +
            ", pressureHigh=" + getPressureHigh() +
            ", pressureLow=" + getPressureLow() +
            ", pressureDiffenceEvaporator=" + getPressureDiffenceEvaporator() +
            ", heatRequest='" + isHeatRequest() + "'" +
            ", userConfirmation='" + isUserConfirmation() + "'" +
            ", alarmExpansionValve='" + isAlarmExpansionValve() + "'" +
            ", incidentFlow='" + isIncidentFlow() + "'" +
            ", incidentCompressor='" + isIncidentCompressor() + "'" +
            ", incidentLowPressure='" + isIncidentLowPressure() + "'" +
            ", incidentHighPressure='" + isIncidentHighPressure() + "'" +
            ", operatingStateWaterPump='" + isOperatingStateWaterPump() + "'" +
            ", operatingStateCompressor='" + isOperatingStateCompressor() + "'" +
            ", calculatedOverheatTemperature=" + getCalculatedOverheatTemperature() +
            ", warningLowPressure='" + isWarningLowPressure() + "'" +
            ", warningHighPressure='" + isWarningHighPressure() + "'" +
//            ", messages.size='" + getMessages().size() + "'" +
//            ", waitCounter='" + getWaitCounter() + "'" +
            "}";
    }
}
