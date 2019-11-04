package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.HeatpumpApp;
import de.vrees.heatpump.domain.Processdata;
import de.vrees.heatpump.repository.ProcessdataRepository;
import de.vrees.heatpump.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    private static final Float DEFAULT_HIGH_PRESSURE = 1F;
    private static final Float UPDATED_HIGH_PRESSURE = 2F;

    private static final Float DEFAULT_LOW_PRESSURE = 1F;
    private static final Float UPDATED_LOW_PRESSURE = 2F;

    private static final Float DEFAULT_EVAPORATING_TEMPERATURE_IN = 1F;
    private static final Float UPDATED_EVAPORATING_TEMPERATURE_IN = 2F;

    private static final Float DEFAULT_EVAPORATING_TEMPERATURE_OUT = 1F;
    private static final Float UPDATED_EVAPORATING_TEMPERATURE_OUT = 2F;

    private static final Float DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR = 1F;
    private static final Float UPDATED_PRESSURE_DIFFENCE_EVAPORATOR = 2F;

    private static final Float DEFAULT_FLOW_TEMPERATURE = 1F;
    private static final Float UPDATED_FLOW_TEMPERATURE = 2F;

    private static final Float DEFAULT_RETURN_TEMPERATURE = 1F;
    private static final Float UPDATED_RETURN_TEMPERATURE = 2F;

    private static final Float DEFAULT_SWITCH_ON_SENSOR_TEMPERATURE = 1F;
    private static final Float UPDATED_SWITCH_ON_SENSOR_TEMPERATURE = 2F;

    private static final Float DEFAULT_OVERHEAT_TEMPERATURE = 1F;
    private static final Float UPDATED_OVERHEAT_TEMPERATURE = 2F;

    private static final Float DEFAULT_EVAPORATOR_OUT_TEMPERATURE = 1F;
    private static final Float UPDATED_EVAPORATOR_OUT_TEMPERATURE = 2F;

    private static final Boolean DEFAULT_HEAT_REQUEST = false;
    private static final Boolean UPDATED_HEAT_REQUEST = true;

    private static final Boolean DEFAULT_USER_CONFIRMATION = false;
    private static final Boolean UPDATED_USER_CONFIRMATION = true;

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

    private static final Boolean DEFAULT_WARNING_LOW_PRESSURE = false;
    private static final Boolean UPDATED_WARNING_LOW_PRESSURE = true;

    private static final Boolean DEFAULT_WARNING_HIGH_PRESSURE = false;
    private static final Boolean UPDATED_WARNING_HIGH_PRESSURE = true;

    private static final Boolean DEFAULT_ALARM_EXPANSION_VALVE = false;
    private static final Boolean UPDATED_ALARM_EXPANSION_VALVE = true;

    @Autowired
    private ProcessdataRepository processdataRepository;

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
        final ProcessdataResource processdataResource = new ProcessdataResource(processdataRepository);
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
            .highPressure(DEFAULT_HIGH_PRESSURE)
            .lowPressure(DEFAULT_LOW_PRESSURE)
            .evaporatingTemperatureIn(DEFAULT_EVAPORATING_TEMPERATURE_IN)
            .evaporatingTemperatureOut(DEFAULT_EVAPORATING_TEMPERATURE_OUT)
            .pressureDiffenceEvaporator(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR)
            .flowTemperature(DEFAULT_FLOW_TEMPERATURE)
            .returnTemperature(DEFAULT_RETURN_TEMPERATURE)
            .switchOnSensorTemperature(DEFAULT_SWITCH_ON_SENSOR_TEMPERATURE)
            .overheatTemperature(DEFAULT_OVERHEAT_TEMPERATURE)
            .evaporatorOutTemperature(DEFAULT_EVAPORATOR_OUT_TEMPERATURE)
            .heatRequest(DEFAULT_HEAT_REQUEST)
            .userConfirmation(DEFAULT_USER_CONFIRMATION)
            .incidentFlow(DEFAULT_INCIDENT_FLOW)
            .incidentCompressor(DEFAULT_INCIDENT_COMPRESSOR)
            .incidentLowPressure(DEFAULT_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(DEFAULT_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(DEFAULT_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(DEFAULT_OPERATING_STATE_COMPRESSOR)
            .warningLowPressure(DEFAULT_WARNING_LOW_PRESSURE)
            .warningHighPressure(DEFAULT_WARNING_HIGH_PRESSURE)
            .alarmExpansionValve(DEFAULT_ALARM_EXPANSION_VALVE);
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
            .highPressure(UPDATED_HIGH_PRESSURE)
            .lowPressure(UPDATED_LOW_PRESSURE)
            .evaporatingTemperatureIn(UPDATED_EVAPORATING_TEMPERATURE_IN)
            .evaporatingTemperatureOut(UPDATED_EVAPORATING_TEMPERATURE_OUT)
            .pressureDiffenceEvaporator(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR)
            .flowTemperature(UPDATED_FLOW_TEMPERATURE)
            .returnTemperature(UPDATED_RETURN_TEMPERATURE)
            .switchOnSensorTemperature(UPDATED_SWITCH_ON_SENSOR_TEMPERATURE)
            .overheatTemperature(UPDATED_OVERHEAT_TEMPERATURE)
            .evaporatorOutTemperature(UPDATED_EVAPORATOR_OUT_TEMPERATURE)
            .heatRequest(UPDATED_HEAT_REQUEST)
            .userConfirmation(UPDATED_USER_CONFIRMATION)
            .incidentFlow(UPDATED_INCIDENT_FLOW)
            .incidentCompressor(UPDATED_INCIDENT_COMPRESSOR)
            .incidentLowPressure(UPDATED_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(UPDATED_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(UPDATED_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(UPDATED_OPERATING_STATE_COMPRESSOR)
            .warningLowPressure(UPDATED_WARNING_LOW_PRESSURE)
            .warningHighPressure(UPDATED_WARNING_HIGH_PRESSURE)
            .alarmExpansionValve(UPDATED_ALARM_EXPANSION_VALVE);
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
        assertThat(testProcessdata.getHighPressure()).isEqualTo(DEFAULT_HIGH_PRESSURE);
        assertThat(testProcessdata.getLowPressure()).isEqualTo(DEFAULT_LOW_PRESSURE);
        assertThat(testProcessdata.getEvaporatingTemperatureIn()).isEqualTo(DEFAULT_EVAPORATING_TEMPERATURE_IN);
        assertThat(testProcessdata.getEvaporatingTemperatureOut()).isEqualTo(DEFAULT_EVAPORATING_TEMPERATURE_OUT);
        assertThat(testProcessdata.getPressureDiffenceEvaporator()).isEqualTo(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR);
        assertThat(testProcessdata.getFlowTemperature()).isEqualTo(DEFAULT_FLOW_TEMPERATURE);
        assertThat(testProcessdata.getReturnTemperature()).isEqualTo(DEFAULT_RETURN_TEMPERATURE);
        assertThat(testProcessdata.getSwitchOnSensorTemperature()).isEqualTo(DEFAULT_SWITCH_ON_SENSOR_TEMPERATURE);
        assertThat(testProcessdata.getOverheatTemperature()).isEqualTo(DEFAULT_OVERHEAT_TEMPERATURE);
        assertThat(testProcessdata.getEvaporatorOutTemperature()).isEqualTo(DEFAULT_EVAPORATOR_OUT_TEMPERATURE);
        assertThat(testProcessdata.isHeatRequest()).isEqualTo(DEFAULT_HEAT_REQUEST);
        assertThat(testProcessdata.isUserConfirmation()).isEqualTo(DEFAULT_USER_CONFIRMATION);
        assertThat(testProcessdata.isIncidentFlow()).isEqualTo(DEFAULT_INCIDENT_FLOW);
        assertThat(testProcessdata.isIncidentCompressor()).isEqualTo(DEFAULT_INCIDENT_COMPRESSOR);
        assertThat(testProcessdata.isIncidentLowPressure()).isEqualTo(DEFAULT_INCIDENT_LOW_PRESSURE);
        assertThat(testProcessdata.isIncidentHighPressure()).isEqualTo(DEFAULT_INCIDENT_HIGH_PRESSURE);
        assertThat(testProcessdata.isOperatingStateWaterPump()).isEqualTo(DEFAULT_OPERATING_STATE_WATER_PUMP);
        assertThat(testProcessdata.isOperatingStateCompressor()).isEqualTo(DEFAULT_OPERATING_STATE_COMPRESSOR);
        assertThat(testProcessdata.isWarningLowPressure()).isEqualTo(DEFAULT_WARNING_LOW_PRESSURE);
        assertThat(testProcessdata.isWarningHighPressure()).isEqualTo(DEFAULT_WARNING_HIGH_PRESSURE);
        assertThat(testProcessdata.isAlarmExpansionValve()).isEqualTo(DEFAULT_ALARM_EXPANSION_VALVE);
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
            .andExpect(jsonPath("$.[*].highPressure").value(hasItem(DEFAULT_HIGH_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].lowPressure").value(hasItem(DEFAULT_LOW_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].evaporatingTemperatureIn").value(hasItem(DEFAULT_EVAPORATING_TEMPERATURE_IN.doubleValue())))
            .andExpect(jsonPath("$.[*].evaporatingTemperatureOut").value(hasItem(DEFAULT_EVAPORATING_TEMPERATURE_OUT.doubleValue())))
            .andExpect(jsonPath("$.[*].pressureDiffenceEvaporator").value(hasItem(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR.doubleValue())))
            .andExpect(jsonPath("$.[*].flowTemperature").value(hasItem(DEFAULT_FLOW_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].returnTemperature").value(hasItem(DEFAULT_RETURN_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].switchOnSensorTemperature").value(hasItem(DEFAULT_SWITCH_ON_SENSOR_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].overheatTemperature").value(hasItem(DEFAULT_OVERHEAT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].evaporatorOutTemperature").value(hasItem(DEFAULT_EVAPORATOR_OUT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].heatRequest").value(hasItem(DEFAULT_HEAT_REQUEST.booleanValue())))
            .andExpect(jsonPath("$.[*].userConfirmation").value(hasItem(DEFAULT_USER_CONFIRMATION.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentFlow").value(hasItem(DEFAULT_INCIDENT_FLOW.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentCompressor").value(hasItem(DEFAULT_INCIDENT_COMPRESSOR.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentLowPressure").value(hasItem(DEFAULT_INCIDENT_LOW_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].incidentHighPressure").value(hasItem(DEFAULT_INCIDENT_HIGH_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingStateWaterPump").value(hasItem(DEFAULT_OPERATING_STATE_WATER_PUMP.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingStateCompressor").value(hasItem(DEFAULT_OPERATING_STATE_COMPRESSOR.booleanValue())))
            .andExpect(jsonPath("$.[*].warningLowPressure").value(hasItem(DEFAULT_WARNING_LOW_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].warningHighPressure").value(hasItem(DEFAULT_WARNING_HIGH_PRESSURE.booleanValue())))
            .andExpect(jsonPath("$.[*].alarmExpansionValve").value(hasItem(DEFAULT_ALARM_EXPANSION_VALVE.booleanValue())));
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
            .andExpect(jsonPath("$.highPressure").value(DEFAULT_HIGH_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.lowPressure").value(DEFAULT_LOW_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.evaporatingTemperatureIn").value(DEFAULT_EVAPORATING_TEMPERATURE_IN.doubleValue()))
            .andExpect(jsonPath("$.evaporatingTemperatureOut").value(DEFAULT_EVAPORATING_TEMPERATURE_OUT.doubleValue()))
            .andExpect(jsonPath("$.pressureDiffenceEvaporator").value(DEFAULT_PRESSURE_DIFFENCE_EVAPORATOR.doubleValue()))
            .andExpect(jsonPath("$.flowTemperature").value(DEFAULT_FLOW_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.returnTemperature").value(DEFAULT_RETURN_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.switchOnSensorTemperature").value(DEFAULT_SWITCH_ON_SENSOR_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.overheatTemperature").value(DEFAULT_OVERHEAT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.evaporatorOutTemperature").value(DEFAULT_EVAPORATOR_OUT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.heatRequest").value(DEFAULT_HEAT_REQUEST.booleanValue()))
            .andExpect(jsonPath("$.userConfirmation").value(DEFAULT_USER_CONFIRMATION.booleanValue()))
            .andExpect(jsonPath("$.incidentFlow").value(DEFAULT_INCIDENT_FLOW.booleanValue()))
            .andExpect(jsonPath("$.incidentCompressor").value(DEFAULT_INCIDENT_COMPRESSOR.booleanValue()))
            .andExpect(jsonPath("$.incidentLowPressure").value(DEFAULT_INCIDENT_LOW_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.incidentHighPressure").value(DEFAULT_INCIDENT_HIGH_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.operatingStateWaterPump").value(DEFAULT_OPERATING_STATE_WATER_PUMP.booleanValue()))
            .andExpect(jsonPath("$.operatingStateCompressor").value(DEFAULT_OPERATING_STATE_COMPRESSOR.booleanValue()))
            .andExpect(jsonPath("$.warningLowPressure").value(DEFAULT_WARNING_LOW_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.warningHighPressure").value(DEFAULT_WARNING_HIGH_PRESSURE.booleanValue()))
            .andExpect(jsonPath("$.alarmExpansionValve").value(DEFAULT_ALARM_EXPANSION_VALVE.booleanValue()));
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
            .highPressure(UPDATED_HIGH_PRESSURE)
            .lowPressure(UPDATED_LOW_PRESSURE)
            .evaporatingTemperatureIn(UPDATED_EVAPORATING_TEMPERATURE_IN)
            .evaporatingTemperatureOut(UPDATED_EVAPORATING_TEMPERATURE_OUT)
            .pressureDiffenceEvaporator(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR)
            .flowTemperature(UPDATED_FLOW_TEMPERATURE)
            .returnTemperature(UPDATED_RETURN_TEMPERATURE)
            .switchOnSensorTemperature(UPDATED_SWITCH_ON_SENSOR_TEMPERATURE)
            .overheatTemperature(UPDATED_OVERHEAT_TEMPERATURE)
            .evaporatorOutTemperature(UPDATED_EVAPORATOR_OUT_TEMPERATURE)
            .heatRequest(UPDATED_HEAT_REQUEST)
            .userConfirmation(UPDATED_USER_CONFIRMATION)
            .incidentFlow(UPDATED_INCIDENT_FLOW)
            .incidentCompressor(UPDATED_INCIDENT_COMPRESSOR)
            .incidentLowPressure(UPDATED_INCIDENT_LOW_PRESSURE)
            .incidentHighPressure(UPDATED_INCIDENT_HIGH_PRESSURE)
            .operatingStateWaterPump(UPDATED_OPERATING_STATE_WATER_PUMP)
            .operatingStateCompressor(UPDATED_OPERATING_STATE_COMPRESSOR)
            .warningLowPressure(UPDATED_WARNING_LOW_PRESSURE)
            .warningHighPressure(UPDATED_WARNING_HIGH_PRESSURE)
            .alarmExpansionValve(UPDATED_ALARM_EXPANSION_VALVE);

        restProcessdataMockMvc.perform(put("/api/processdata")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcessdata)))
            .andExpect(status().isOk());

        // Validate the Processdata in the database
        List<Processdata> processdataList = processdataRepository.findAll();
        assertThat(processdataList).hasSize(databaseSizeBeforeUpdate);
        Processdata testProcessdata = processdataList.get(processdataList.size() - 1);
        assertThat(testProcessdata.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testProcessdata.getHighPressure()).isEqualTo(UPDATED_HIGH_PRESSURE);
        assertThat(testProcessdata.getLowPressure()).isEqualTo(UPDATED_LOW_PRESSURE);
        assertThat(testProcessdata.getEvaporatingTemperatureIn()).isEqualTo(UPDATED_EVAPORATING_TEMPERATURE_IN);
        assertThat(testProcessdata.getEvaporatingTemperatureOut()).isEqualTo(UPDATED_EVAPORATING_TEMPERATURE_OUT);
        assertThat(testProcessdata.getPressureDiffenceEvaporator()).isEqualTo(UPDATED_PRESSURE_DIFFENCE_EVAPORATOR);
        assertThat(testProcessdata.getFlowTemperature()).isEqualTo(UPDATED_FLOW_TEMPERATURE);
        assertThat(testProcessdata.getReturnTemperature()).isEqualTo(UPDATED_RETURN_TEMPERATURE);
        assertThat(testProcessdata.getSwitchOnSensorTemperature()).isEqualTo(UPDATED_SWITCH_ON_SENSOR_TEMPERATURE);
        assertThat(testProcessdata.getOverheatTemperature()).isEqualTo(UPDATED_OVERHEAT_TEMPERATURE);
        assertThat(testProcessdata.getEvaporatorOutTemperature()).isEqualTo(UPDATED_EVAPORATOR_OUT_TEMPERATURE);
        assertThat(testProcessdata.isHeatRequest()).isEqualTo(UPDATED_HEAT_REQUEST);
        assertThat(testProcessdata.isUserConfirmation()).isEqualTo(UPDATED_USER_CONFIRMATION);
        assertThat(testProcessdata.isIncidentFlow()).isEqualTo(UPDATED_INCIDENT_FLOW);
        assertThat(testProcessdata.isIncidentCompressor()).isEqualTo(UPDATED_INCIDENT_COMPRESSOR);
        assertThat(testProcessdata.isIncidentLowPressure()).isEqualTo(UPDATED_INCIDENT_LOW_PRESSURE);
        assertThat(testProcessdata.isIncidentHighPressure()).isEqualTo(UPDATED_INCIDENT_HIGH_PRESSURE);
        assertThat(testProcessdata.isOperatingStateWaterPump()).isEqualTo(UPDATED_OPERATING_STATE_WATER_PUMP);
        assertThat(testProcessdata.isOperatingStateCompressor()).isEqualTo(UPDATED_OPERATING_STATE_COMPRESSOR);
        assertThat(testProcessdata.isWarningLowPressure()).isEqualTo(UPDATED_WARNING_LOW_PRESSURE);
        assertThat(testProcessdata.isWarningHighPressure()).isEqualTo(UPDATED_WARNING_HIGH_PRESSURE);
        assertThat(testProcessdata.isAlarmExpansionValve()).isEqualTo(UPDATED_ALARM_EXPANSION_VALVE);
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

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processdata.class);
        Processdata processdata1 = new Processdata();
        processdata1.setId("id1");
        Processdata processdata2 = new Processdata();
        processdata2.setId(processdata1.getId());
        assertThat(processdata1).isEqualTo(processdata2);
        processdata2.setId("id2");
        assertThat(processdata1).isNotEqualTo(processdata2);
        processdata1.setId(null);
        assertThat(processdata1).isNotEqualTo(processdata2);
    }
}
