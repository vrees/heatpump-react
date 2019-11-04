package de.vrees.heatpump.domain;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Processdata.
 */
@Document(collection = "processdata")
public class Processdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("timestamp")
    private Instant timestamp;

    /**
     * Hochdruck Kältekreis in bar
     */
    @ApiModelProperty(value = "Hochdruck Kältekreis in bar")
    @Field("high_pressure")
    private Float highPressure;

    /**
     * Niederdruck Kältekreis in bar
     */
    @ApiModelProperty(value = "Niederdruck Kältekreis in bar")
    @Field("low_pressure")
    private Float lowPressure;

    /**
     * Verdampfungstemperatur in °C
     */
    @ApiModelProperty(value = "Verdampfungstemperatur in °C")
    @Field("evaporating_temperature_in")
    private Float evaporatingTemperatureIn;

    /**
     * Verdampfungstemperatur out in °C
     */
    @ApiModelProperty(value = "Verdampfungstemperatur out in °C")
    @Field("evaporating_temperature_out")
    private Float evaporatingTemperatureOut;

    /**
     * Druckdifferenz Verdampfer in mbar
     */
    @ApiModelProperty(value = "Druckdifferenz Verdampfer in mbar")
    @Field("pressure_diffence_evaporator")
    private Float pressureDiffenceEvaporator;

    /**
     * VorlaufTemperatur in °C
     */
    @ApiModelProperty(value = "VorlaufTemperatur in °C")
    @Field("flow_temperature")
    private Float flowTemperature;

    /**
     * Rücklauf-Temperatur in °C
     */
    @ApiModelProperty(value = "Rücklauf-Temperatur in °C")
    @Field("return_temperature")
    private Float returnTemperature;

    /**
     * schaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur in °C
     */
    @ApiModelProperty(value = "schaltFuehlerTemperatur: Ein-/Aus-SchaltFühler misst die Temperatur in °C")
    @Field("switch_on_sensor_temperature")
    private Float switchOnSensorTemperature;

    /**
     * Überhitzung: Kühlmittel-Temperatur am Ausgang des Verdampfers (berechnet ?)
     */
    @ApiModelProperty(value = "Überhitzung: Kühlmittel-Temperatur am Ausgang des Verdampfers (berechnet ?)")
    @Field("overheat_temperature")
    private Float overheatTemperature;

    /**
     * SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters. Wird zusammen mit dem Druck im Verdampfer zur Bestimmung der Überhitzung benötigt
     */
    @ApiModelProperty(value = "SaugTemperatur: Kühlmittel-Temperatur am Ausgang des Verdampfers vor dem Eingang des Verdichters. Wird zusammen mit dem Druck im Verdampfer zur Bestimmung der Überhitzung benötigt")
    @Field("evaporator_out_temperature")
    private Float evaporatorOutTemperature;

    /**
     * Wärme Anforderung
     */
    @ApiModelProperty(value = "Wärme Anforderung")
    @Field("heat_request")
    private Boolean heatRequest;

    /**
     * EinAusQuittierung
     */
    @ApiModelProperty(value = "EinAusQuittierung")
    @Field("user_confirmation")
    private Boolean userConfirmation;

    /**
     * Stoerung Durchfluss - minimale Druchlussmenge unterschritten
     */
    @ApiModelProperty(value = "Stoerung Durchfluss - minimale Druchlussmenge unterschritten")
    @Field("incident_flow")
    private Boolean incidentFlow;

    /**
     * Stoerung Verdichter / Motorschutzschalter
     */
    @ApiModelProperty(value = "Stoerung Verdichter / Motorschutzschalter")
    @Field("incident_compressor")
    private Boolean incidentCompressor;

    /**
     * Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck
     */
    @ApiModelProperty(value = "Stoerung Niederdruck: Ranco-Thermostat meldet zu niedrigen Niederdruck")
    @Field("incident_low_pressure")
    private Boolean incidentLowPressure;

    /**
     * Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck
     */
    @ApiModelProperty(value = "Stoerung Hochdruck Ranco-Thermostat meldet zu hohen Hochdruck")
    @Field("incident_high_pressure")
    private Boolean incidentHighPressure;

    /**
     * Status Heizungspume: Ein/Aus
     */
    @ApiModelProperty(value = "Status Heizungspume: Ein/Aus")
    @Field("operating_state_water_pump")
    private Boolean operatingStateWaterPump;

    /**
     * Status Verdichter: Ein/Aus
     */
    @ApiModelProperty(value = "Status Verdichter: Ein/Aus")
    @Field("operating_state_compressor")
    private Boolean operatingStateCompressor;

    /**
     * Warung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)
     */
    @ApiModelProperty(value = "Warung Niederdruck (Soft-Wert falls gemessener Niederdruck unter konfigurierte Grenze fällt)")
    @Field("warning_low_pressure")
    private Boolean warningLowPressure;

    /**
     * Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)
     */
    @ApiModelProperty(value = "Warnung Hochdruck (Soft-Wert falls gemessener Hochdruck über konfigurierte Grenze steigt)")
    @Field("warning_high_pressure")
    private Boolean warningHighPressure;

    /**
     * Alarm des Elektronischen Expansionsentils EEV
     */
    @ApiModelProperty(value = "Alarm des Elektronischen Expansionsentils EEV")
    @Field("alarm_expansion_valve")
    private Boolean alarmExpansionValve;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Float getHighPressure() {
        return highPressure;
    }

    public Processdata highPressure(Float highPressure) {
        this.highPressure = highPressure;
        return this;
    }

    public void setHighPressure(Float highPressure) {
        this.highPressure = highPressure;
    }

    public Float getLowPressure() {
        return lowPressure;
    }

    public Processdata lowPressure(Float lowPressure) {
        this.lowPressure = lowPressure;
        return this;
    }

    public void setLowPressure(Float lowPressure) {
        this.lowPressure = lowPressure;
    }

    public Float getEvaporatingTemperatureIn() {
        return evaporatingTemperatureIn;
    }

    public Processdata evaporatingTemperatureIn(Float evaporatingTemperatureIn) {
        this.evaporatingTemperatureIn = evaporatingTemperatureIn;
        return this;
    }

    public void setEvaporatingTemperatureIn(Float evaporatingTemperatureIn) {
        this.evaporatingTemperatureIn = evaporatingTemperatureIn;
    }

    public Float getEvaporatingTemperatureOut() {
        return evaporatingTemperatureOut;
    }

    public Processdata evaporatingTemperatureOut(Float evaporatingTemperatureOut) {
        this.evaporatingTemperatureOut = evaporatingTemperatureOut;
        return this;
    }

    public void setEvaporatingTemperatureOut(Float evaporatingTemperatureOut) {
        this.evaporatingTemperatureOut = evaporatingTemperatureOut;
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

    public Float getFlowTemperature() {
        return flowTemperature;
    }

    public Processdata flowTemperature(Float flowTemperature) {
        this.flowTemperature = flowTemperature;
        return this;
    }

    public void setFlowTemperature(Float flowTemperature) {
        this.flowTemperature = flowTemperature;
    }

    public Float getReturnTemperature() {
        return returnTemperature;
    }

    public Processdata returnTemperature(Float returnTemperature) {
        this.returnTemperature = returnTemperature;
        return this;
    }

    public void setReturnTemperature(Float returnTemperature) {
        this.returnTemperature = returnTemperature;
    }

    public Float getSwitchOnSensorTemperature() {
        return switchOnSensorTemperature;
    }

    public Processdata switchOnSensorTemperature(Float switchOnSensorTemperature) {
        this.switchOnSensorTemperature = switchOnSensorTemperature;
        return this;
    }

    public void setSwitchOnSensorTemperature(Float switchOnSensorTemperature) {
        this.switchOnSensorTemperature = switchOnSensorTemperature;
    }

    public Float getOverheatTemperature() {
        return overheatTemperature;
    }

    public Processdata overheatTemperature(Float overheatTemperature) {
        this.overheatTemperature = overheatTemperature;
        return this;
    }

    public void setOverheatTemperature(Float overheatTemperature) {
        this.overheatTemperature = overheatTemperature;
    }

    public Float getEvaporatorOutTemperature() {
        return evaporatorOutTemperature;
    }

    public Processdata evaporatorOutTemperature(Float evaporatorOutTemperature) {
        this.evaporatorOutTemperature = evaporatorOutTemperature;
        return this;
    }

    public void setEvaporatorOutTemperature(Float evaporatorOutTemperature) {
        this.evaporatorOutTemperature = evaporatorOutTemperature;
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
            ", highPressure=" + getHighPressure() +
            ", lowPressure=" + getLowPressure() +
            ", evaporatingTemperatureIn=" + getEvaporatingTemperatureIn() +
            ", evaporatingTemperatureOut=" + getEvaporatingTemperatureOut() +
            ", pressureDiffenceEvaporator=" + getPressureDiffenceEvaporator() +
            ", flowTemperature=" + getFlowTemperature() +
            ", returnTemperature=" + getReturnTemperature() +
            ", switchOnSensorTemperature=" + getSwitchOnSensorTemperature() +
            ", overheatTemperature=" + getOverheatTemperature() +
            ", evaporatorOutTemperature=" + getEvaporatorOutTemperature() +
            ", heatRequest='" + isHeatRequest() + "'" +
            ", userConfirmation='" + isUserConfirmation() + "'" +
            ", incidentFlow='" + isIncidentFlow() + "'" +
            ", incidentCompressor='" + isIncidentCompressor() + "'" +
            ", incidentLowPressure='" + isIncidentLowPressure() + "'" +
            ", incidentHighPressure='" + isIncidentHighPressure() + "'" +
            ", operatingStateWaterPump='" + isOperatingStateWaterPump() + "'" +
            ", operatingStateCompressor='" + isOperatingStateCompressor() + "'" +
            ", warningLowPressure='" + isWarningLowPressure() + "'" +
            ", warningHighPressure='" + isWarningHighPressure() + "'" +
            ", alarmExpansionValve='" + isAlarmExpansionValve() + "'" +
            "}";
    }
}
