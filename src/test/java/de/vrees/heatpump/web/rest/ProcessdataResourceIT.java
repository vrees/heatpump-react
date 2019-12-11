package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.HeatpumpApp;
import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.repository.ProcessdataRepository;
import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import de.vrees.heatpump.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static de.vrees.heatpump.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration tests for the {@link ProcessdataResource} REST controller.
 */
@SpringBootTest(classes = HeatpumpApp.class)
public class ProcessdataResourceIT {

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_TEMPERATURE_EVAPORATING_IN = 1F;
    private static final Float UPDATED_TEMPERATURE_EVAPORATING_IN = 2F;

    private static final Float DEFAULT_TEMPERATURE_EVAPORATING_OUT = 1F;
    private static final Float UPDATED_TEMPERATURE_EVAPORATING_OUT = 2F;

    private static final Float DEFAULT_TEMPERATURE_FLOW = 1F;
    private static final Float UPDATED_TEMPERATURE_FLOW = 2F;

    private static final Float DEFAULT_TEMPERATURE_RETURN = 1F;
    private static final Float UPDATED_TEMPERATURE_RETURN = 2F;

    private static final Float DEFAULT_TEMPERATURE_SWITCH_ON_SENSOR = 1F;
    private static final Float UPDATED_TEMPERATURE_SWITCH_ON_SENSOR = 2F;

    private static final Float DEFAULT_TEMPERATURE_OVERHEATED_GAS = 1F;
    private static final Float UPDATED_TEMPERATURE_OVERHEATED_GAS = 2F;

    private static final Float DEFAULT_PRESSURE_HIGH = 1F;
    private static final Float UPDATED_PRESSURE_HIGH = 2F;

    private static final Float DEFAULT_PRESSURE_LOW = 1F;
    private static final Float UPDATED_PRESSURE_LOW = 2F;

    private static final Float DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR = 1F;
    private static final Float UPDATED_PRESSURE_DIFFENCE_EVAPORATOR = 2F;

    private static final Boolean DEFAULT_HEAT_REQUEST = false;
    private static final Boolean UPDATED_HEAT_REQUEST = true;

    private static final Boolean DEFAULT_USER_CONFIRMATION = false;
    private static final Boolean UPDATED_USER_CONFIRMATION = true;

    private static final Boolean DEFAULT_ALARM_EXPANSION_VALVE = false;
    private static final Boolean UPDATED_ALARM_EXPANSION_VALVE = true;

    private static final Boolean DEFAULT_INCIDENT_FLOW = false;
    private static final Boolean UPDATED_INCIDENT_FLOW = true;

    private static final Boolean DEFAULT_INCIDENT_COMPRESSOR = false;
    private static final Boolean UPDATED_INCIDENT_COMPRESSOR = true;

    private static final Boolean DEFAULT_INCIDENT_LOW_PRESSURE = false;
    private static final Boolean UPDATED_INCIDENT_LOW_PRESSURE = true;

    private static final Boolean DEFAULT_INCIDENT_HIGH_PRESSURE = false;
    private static final Boolean UPDATED_INCIDENT_HIGH_PRESSURE = true;

    private static final Boolean DEFAULT_OPERATING_STATE_WATER_PUMP = false;
    private static final Boolean UPDATED_OPERATING_STATE_WATER_PUMP = true;

    private static final Boolean DEFAULT_OPERATING_STATE_COMPRESSOR = false;
    private static final Boolean UPDATED_OPERATING_STATE_COMPRESSOR = true;

    private static final Float DEFAULT_CALCULATED_OVERHEAT_TEMPERATURE = 1F;
    private static final Float UPDATED_CALCULATED_OVERHEAT_TEMPERATURE = 2F;

    private static final Boolean DEFAULT_WARNING_LOW_PRESSURE = false;
    private static final Boolean UPDATED_WARNING_LOW_PRESSURE = true;

    private static final Boolean DEFAULT_WARNING_HIGH_PRESSURE = false;
    private static final Boolean UPDATED_WARNING_HIGH_PRESSURE = true;

    private static final States DEFAULT_STATE = States.OFF;
    private static final States UPDATED_STATE = States.READY;

    @Autowired
    private ProcessdataRepository processdataRepository;

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProcessdataMockMvc;

    private Processdata processdata;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessdataResource processdataResource = new ProcessdataResource(processdataRepository, stateMachine);
        this.restProcessdataMockMvc = MockMvcBuilders.standaloneSetup(processdataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processdata createEntity() {
        Processdata processdata = new Processdata()
            .timestamp(DEFAULT_TIMESTAMP)
            .temperatureEvaporatingIn(DEFAULT_TEMPERATURE_EVAPORATING_IN)
            .temperatureEvaporatingOut(DEFAULT_TEMPERATURE_EVAPORATING_OUT)
            .temperatureFlow(DEFAULT_TEMPERATURE_FLOW)
            .temperatureReturn(DEFAULT_TEMPERATURE_RETURN)
            .temperatureSwitchOnSensor(DEFAULT_TEMPERATURE_SWITCH_ON_SENSOR)
            .temperatureOverheatedGas(DEFAULT_TEMPERATURE_OVERHEATED_GAS)
            .pressureHigh(DEFAULT_PRESSURE_HIGH)
            .pressureLow(DEFAULT_PRESSURE_LOW)
            .pressureDiffenceEvaporator(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR)
            .heatRequest(DEFAULT_HEAT_REQUEST)
            .userConfirmation(DEFAULT_USER_CONFIRMATION)
            .alarmExpansionValve(DEFAULT_ALARM_EXPANSION_VALVE)
            .incidentFlow(DEFAULT_INCIDENT_FLOW)
            .incidentCompressor(DEFAULT_INCIDENT_COMPRESSOR)
            .incidentLowPressure(DEFAULT_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(DEFAULT_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(DEFAULT_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(DEFAULT_OPERATING_STATE_COMPRESSOR)
            .calculatedOverheatTemperature(DEFAULT_CALCULATED_OVERHEAT_TEMPERATURE)
            .warningLowPressure(DEFAULT_WARNING_LOW_PRESSURE)
            .warningHighPressure(DEFAULT_WARNING_HIGH_PRESSURE)
            .state(DEFAULT_STATE);
        return processdata;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processdata createUpdatedEntity() {
        Processdata processdata = new Processdata()
            .timestamp(UPDATED_TIMESTAMP)
            .temperatureEvaporatingIn(UPDATED_TEMPERATURE_EVAPORATING_IN)
            .temperatureEvaporatingOut(UPDATED_TEMPERATURE_EVAPORATING_OUT)
            .temperatureFlow(UPDATED_TEMPERATURE_FLOW)
            .temperatureReturn(UPDATED_TEMPERATURE_RETURN)
            .temperatureSwitchOnSensor(UPDATED_TEMPERATURE_SWITCH_ON_SENSOR)
            .temperatureOverheatedGas(UPDATED_TEMPERATURE_OVERHEATED_GAS)
            .pressureHigh(UPDATED_PRESSURE_HIGH)
            .pressureLow(UPDATED_PRESSURE_LOW)
            .pressureDiffenceEvaporator(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR)
            .heatRequest(UPDATED_HEAT_REQUEST)
            .userConfirmation(UPDATED_USER_CONFIRMATION)
            .alarmExpansionValve(UPDATED_ALARM_EXPANSION_VALVE)
            .incidentFlow(UPDATED_INCIDENT_FLOW)
            .incidentCompressor(UPDATED_INCIDENT_COMPRESSOR)
            .incidentLowPressure(UPDATED_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(UPDATED_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(UPDATED_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(UPDATED_OPERATING_STATE_COMPRESSOR)
            .calculatedOverheatTemperature(UPDATED_CALCULATED_OVERHEAT_TEMPERATURE)
            .warningLowPressure(UPDATED_WARNING_LOW_PRESSURE)
            .warningHighPressure(UPDATED_WARNING_HIGH_PRESSURE)
            .state(UPDATED_STATE);
        return processdata;
    }

    @BeforeEach
    public void initTest() {
        processdataRepository.deleteAll();
        processdata = createEntity();
    }

    @Test
    public void createProcessdata() throws Exception {
        int databaseSizeBeforeCreate = processdataRepository.findAll().size();

        // Create the Processdata
        restProcessdataMockMvc.perform(post("/api/processdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processdata)))
            .andExpect(status().isCreated());

        // Validate the Processdata in the database
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeCreate + 1);
        Processdata testProcessdata = processdataList.get(processdataList.size() - 1);
        assertThat(testProcessdata.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testProcessdata.getTemperatureEvaporatingIn()).isEqualTo(DEFAULT_TEMPERATURE_EVAPORATING_IN);
        assertThat(testProcessdata.getTemperatureEvaporatingOut()).isEqualTo(DEFAULT_TEMPERATURE_EVAPORATING_OUT);
        assertThat(testProcessdata.getTemperatureFlow()).isEqualTo(DEFAULT_TEMPERATURE_FLOW);
        assertThat(testProcessdata.getTemperatureReturn()).isEqualTo(DEFAULT_TEMPERATURE_RETURN);
        assertThat(testProcessdata.getTemperatureSwitchOnSensor()).isEqualTo(DEFAULT_TEMPERATURE_SWITCH_ON_SENSOR);
        assertThat(testProcessdata.getTemperatureOverheatedGas()).isEqualTo(DEFAULT_TEMPERATURE_OVERHEATED_GAS);
        assertThat(testProcessdata.getPressureHigh()).isEqualTo(DEFAULT_PRESSURE_HIGH);
        assertThat(testProcessdata.getPressureLow()).isEqualTo(DEFAULT_PRESSURE_LOW);
        assertThat(testProcessdata.getPressureDiffenceEvaporator()).isEqualTo(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR);
        assertThat(testProcessdata.isHeatRequest()).isEqualTo(DEFAULT_HEAT_REQUEST);
        assertThat(testProcessdata.isUserConfirmation()).isEqualTo(DEFAULT_USER_CONFIRMATION);
        assertThat(testProcessdata.isAlarmExpansionValve()).isEqualTo(DEFAULT_ALARM_EXPANSION_VALVE);
        assertThat(testProcessdata.isIncidentFlow()).isEqualTo(DEFAULT_INCIDENT_FLOW);
        assertThat(testProcessdata.isIncidentCompressor()).isEqualTo(DEFAULT_INCIDENT_COMPRESSOR);
        assertThat(testProcessdata.isIncidentLowPressure()).isEqualTo(DEFAULT_INCIDENT_LOW_PRESSURE);
        assertThat(testProcessdata.isIncidentHighPressure()).isEqualTo(DEFAULT_INCIDENT_HIGH_PRESSURE);
        assertThat(testProcessdata.isOperatingStateWaterPump()).isEqualTo(DEFAULT_OPERATING_STATE_WATER_PUMP);
        assertThat(testProcessdata.isOperatingStateCompressor()).isEqualTo(DEFAULT_OPERATING_STATE_COMPRESSOR);
        assertThat(testProcessdata.getCalculatedOverheatTemperature()).isEqualTo(DEFAULT_CALCULATED_OVERHEAT_TEMPERATURE);
        assertThat(testProcessdata.isWarningLowPressure()).isEqualTo(DEFAULT_WARNING_LOW_PRESSURE);
        assertThat(testProcessdata.isWarningHighPressure()).isEqualTo(DEFAULT_WARNING_HIGH_PRESSURE);
        assertThat(testProcessdata.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void createProcessdataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processdataRepository.findAll().size();

        // Create the Processdata with an existing ID
        processdata.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessdataMockMvc.perform(post("/api/processdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processdata)))
            .andExpect(status().isBadRequest());

        // Validate the Processdata in the database
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProcessdata() throws Exception {
        // Initialize the database
        processdataRepository.save(processdata);

        // Get all the processdataList
        restProcessdataMockMvc.perform(get("/api/processdata?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processdata.getId())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].temperatureEvaporatingIn").value(hasItem(DEFAULT_TEMPERATURE_EVAPORATING_IN.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatureEvaporatingOut").value(hasItem(DEFAULT_TEMPERATURE_EVAPORATING_OUT.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatureFlow").value(hasItem(DEFAULT_TEMPERATURE_FLOW.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatureReturn").value(hasItem(DEFAULT_TEMPERATURE_RETURN.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatureSwitchOnSensor").value(hasItem(DEFAULT_TEMPERATURE_SWITCH_ON_SENSOR.doubleValue())))
            .andExpect(jsonPath("$.[*].temperatureOverheatedGas").value(hasItem(DEFAULT_TEMPERATURE_OVERHEATED_GAS.doubleValue())))
            .andExpect(jsonPath("$.[*].pressureHigh").value(hasItem(DEFAULT_PRESSURE_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].pressureLow").value(hasItem(DEFAULT_PRESSURE_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].pressureDiffenceEvaporator").value(hasItem(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR.doubleValue())))
            .andExpect(jsonPath("$.[*].heatRequest").value(hasItem(DEFAULT_HEAT_REQUEST.booleanValue())))
            .andExpect(jsonPath("$.[*].userConfirmation").value(hasItem(DEFAULT_USER_CONFIRMATION.booleanValue())))
            .andExpect(jsonPath("$.[*].alarmExpansionValve").value(hasItem(DEFAULT_ALARM_EXPANSION_VALVE.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentFlow").value(hasItem(DEFAULT_INCIDENT_FLOW.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentCompressor").value(hasItem(DEFAULT_INCIDENT_COMPRESSOR.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentLowPressure").value(hasItem(DEFAULT_INCIDENT_LOW_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentHighPressure").value(hasItem(DEFAULT_INCIDENT_HIGH_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingStateWaterPump").value(hasItem(DEFAULT_OPERATING_STATE_WATER_PUMP.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingStateCompressor").value(hasItem(DEFAULT_OPERATING_STATE_COMPRESSOR.booleanValue())))
            .andExpect(jsonPath("$.[*].calculatedOverheatTemperature").value(hasItem(DEFAULT_CALCULATED_OVERHEAT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].warningLowPressure").value(hasItem(DEFAULT_WARNING_LOW_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].warningHighPressure").value(hasItem(DEFAULT_WARNING_HIGH_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    public void getProcessdata() throws Exception {
        // Initialize the database
        processdataRepository.save(processdata);

        // Get the processdata
        restProcessdataMockMvc.perform(get("/api/processdata/{id}", processdata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processdata.getId()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.temperatureEvaporatingIn").value(DEFAULT_TEMPERATURE_EVAPORATING_IN.doubleValue()))
            .andExpect(jsonPath("$.temperatureEvaporatingOut").value(DEFAULT_TEMPERATURE_EVAPORATING_OUT.doubleValue()))
            .andExpect(jsonPath("$.temperatureFlow").value(DEFAULT_TEMPERATURE_FLOW.doubleValue()))
            .andExpect(jsonPath("$.temperatureReturn").value(DEFAULT_TEMPERATURE_RETURN.doubleValue()))
            .andExpect(jsonPath("$.temperatureSwitchOnSensor").value(DEFAULT_TEMPERATURE_SWITCH_ON_SENSOR.doubleValue()))
            .andExpect(jsonPath("$.temperatureOverheatedGas").value(DEFAULT_TEMPERATURE_OVERHEATED_GAS.doubleValue()))
            .andExpect(jsonPath("$.pressureHigh").value(DEFAULT_PRESSURE_HIGH.doubleValue()))
            .andExpect(jsonPath("$.pressureLow").value(DEFAULT_PRESSURE_LOW.doubleValue()))
            .andExpect(jsonPath("$.pressureDiffenceEvaporator").value(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR.doubleValue()))
            .andExpect(jsonPath("$.heatRequest").value(DEFAULT_HEAT_REQUEST.booleanValue()))
            .andExpect(jsonPath("$.userConfirmation").value(DEFAULT_USER_CONFIRMATION.booleanValue()))
            .andExpect(jsonPath("$.alarmExpansionValve").value(DEFAULT_ALARM_EXPANSION_VALVE.booleanValue()))
            .andExpect(jsonPath("$.incidentFlow").value(DEFAULT_INCIDENT_FLOW.booleanValue()))
            .andExpect(jsonPath("$.incidentCompressor").value(DEFAULT_INCIDENT_COMPRESSOR.booleanValue()))
            .andExpect(jsonPath("$.incidentLowPressure").value(DEFAULT_INCIDENT_LOW_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.incidentHighPressure").value(DEFAULT_INCIDENT_HIGH_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.operatingStateWaterPump").value(DEFAULT_OPERATING_STATE_WATER_PUMP.booleanValue()))
            .andExpect(jsonPath("$.operatingStateCompressor").value(DEFAULT_OPERATING_STATE_COMPRESSOR.booleanValue()))
            .andExpect(jsonPath("$.calculatedOverheatTemperature").value(DEFAULT_CALCULATED_OVERHEAT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.warningLowPressure").value(DEFAULT_WARNING_LOW_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.warningHighPressure").value(DEFAULT_WARNING_HIGH_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    public void getNonExistingProcessdata() throws Exception {
        // Get the processdata
        restProcessdataMockMvc.perform(get("/api/processdata/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProcessdata() throws Exception {
        // Initialize the database
        processdataRepository.save(processdata);

        int databaseSizeBeforeUpdate = processdataRepository.findAll().size();

        // Update the processdata
        Processdata updatedProcessdata = processdataRepository.findById(processdata.getId()).get();
        updatedProcessdata
            .timestamp(UPDATED_TIMESTAMP)
            .temperatureEvaporatingIn(UPDATED_TEMPERATURE_EVAPORATING_IN)
            .temperatureEvaporatingOut(UPDATED_TEMPERATURE_EVAPORATING_OUT)
            .temperatureFlow(UPDATED_TEMPERATURE_FLOW)
            .temperatureReturn(UPDATED_TEMPERATURE_RETURN)
            .temperatureSwitchOnSensor(UPDATED_TEMPERATURE_SWITCH_ON_SENSOR)
            .temperatureOverheatedGas(UPDATED_TEMPERATURE_OVERHEATED_GAS)
            .pressureHigh(UPDATED_PRESSURE_HIGH)
            .pressureLow(UPDATED_PRESSURE_LOW)
            .pressureDiffenceEvaporator(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR)
            .heatRequest(UPDATED_HEAT_REQUEST)
            .userConfirmation(UPDATED_USER_CONFIRMATION)
            .alarmExpansionValve(UPDATED_ALARM_EXPANSION_VALVE)
            .incidentFlow(UPDATED_INCIDENT_FLOW)
            .incidentCompressor(UPDATED_INCIDENT_COMPRESSOR)
            .incidentLowPressure(UPDATED_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(UPDATED_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(UPDATED_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(UPDATED_OPERATING_STATE_COMPRESSOR)
            .calculatedOverheatTemperature(UPDATED_CALCULATED_OVERHEAT_TEMPERATURE)
            .warningLowPressure(UPDATED_WARNING_LOW_PRESSURE)
            .warningHighPressure(UPDATED_WARNING_HIGH_PRESSURE)
            .state(UPDATED_STATE);

        restProcessdataMockMvc.perform(put("/api/processdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessdata)))
            .andExpect(status().isOk());

        // Validate the Processdata in the database
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeUpdate);
        Processdata testProcessdata = processdataList.get(processdataList.size() - 1);
        assertThat(testProcessdata.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testProcessdata.getTemperatureEvaporatingIn()).isEqualTo(UPDATED_TEMPERATURE_EVAPORATING_IN);
        assertThat(testProcessdata.getTemperatureEvaporatingOut()).isEqualTo(UPDATED_TEMPERATURE_EVAPORATING_OUT);
        assertThat(testProcessdata.getTemperatureFlow()).isEqualTo(UPDATED_TEMPERATURE_FLOW);
        assertThat(testProcessdata.getTemperatureReturn()).isEqualTo(UPDATED_TEMPERATURE_RETURN);
        assertThat(testProcessdata.getTemperatureSwitchOnSensor()).isEqualTo(UPDATED_TEMPERATURE_SWITCH_ON_SENSOR);
        assertThat(testProcessdata.getTemperatureOverheatedGas()).isEqualTo(UPDATED_TEMPERATURE_OVERHEATED_GAS);
        assertThat(testProcessdata.getPressureHigh()).isEqualTo(UPDATED_PRESSURE_HIGH);
        assertThat(testProcessdata.getPressureLow()).isEqualTo(UPDATED_PRESSURE_LOW);
        assertThat(testProcessdata.getPressureDiffenceEvaporator()).isEqualTo(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR);
        assertThat(testProcessdata.isHeatRequest()).isEqualTo(UPDATED_HEAT_REQUEST);
        assertThat(testProcessdata.isUserConfirmation()).isEqualTo(UPDATED_USER_CONFIRMATION);
        assertThat(testProcessdata.isAlarmExpansionValve()).isEqualTo(UPDATED_ALARM_EXPANSION_VALVE);
        assertThat(testProcessdata.isIncidentFlow()).isEqualTo(UPDATED_INCIDENT_FLOW);
        assertThat(testProcessdata.isIncidentCompressor()).isEqualTo(UPDATED_INCIDENT_COMPRESSOR);
        assertThat(testProcessdata.isIncidentLowPressure()).isEqualTo(UPDATED_INCIDENT_LOW_PRESSURE);
        assertThat(testProcessdata.isIncidentHighPressure()).isEqualTo(UPDATED_INCIDENT_HIGH_PRESSURE);
        assertThat(testProcessdata.isOperatingStateWaterPump()).isEqualTo(UPDATED_OPERATING_STATE_WATER_PUMP);
        assertThat(testProcessdata.isOperatingStateCompressor()).isEqualTo(UPDATED_OPERATING_STATE_COMPRESSOR);
        assertThat(testProcessdata.getCalculatedOverheatTemperature()).isEqualTo(UPDATED_CALCULATED_OVERHEAT_TEMPERATURE);
        assertThat(testProcessdata.isWarningLowPressure()).isEqualTo(UPDATED_WARNING_LOW_PRESSURE);
        assertThat(testProcessdata.isWarningHighPressure()).isEqualTo(UPDATED_WARNING_HIGH_PRESSURE);
        assertThat(testProcessdata.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void updateNonExistingProcessdata() throws Exception {
        int databaseSizeBeforeUpdate = processdataRepository.findAll().size();

        // Create the Processdata

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessdataMockMvc.perform(put("/api/processdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processdata)))
            .andExpect(status().isBadRequest());

        // Validate the Processdata in the database
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProcessdata() throws Exception {
        // Initialize the database
        processdataRepository.save(processdata);

        int databaseSizeBeforeDelete = processdataRepository.findAll().size();

        // Delete the processdata
        restProcessdataMockMvc.perform(delete("/api/processdata/{id}", processdata.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
