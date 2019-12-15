package de.vrees.heatpump.web.rest;

import de.vrees.heatpump.HeatpumpApp;
import de.vrees.heatpump.domain.FailureMessage;
import de.vrees.heatpump.repository.FailureMessageRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static de.vrees.heatpump.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.vrees.heatpump.domain.enumeration.FailureLevel;
/**
 * Integration tests for the {@link FailureMessageResource} REST controller.
 */
@SpringBootTest(classes = HeatpumpApp.class)
public class FailureMessageResourceIT {

    private static final FailureLevel DEFAULT_FAILURE_LEVEL = FailureLevel.ERROR;
    private static final FailureLevel UPDATED_FAILURE_LEVEL = FailureLevel.WARNING;

    private static final String DEFAULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MSG = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    @Autowired
    private FailureMessageRepository failureMessageRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restFailureMessageMockMvc;

    private FailureMessage failureMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FailureMessageResource failureMessageResource = new FailureMessageResource(failureMessageRepository);
        this.restFailureMessageMockMvc = MockMvcBuilders.standaloneSetup(failureMessageResource)
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
    public static FailureMessage createEntity(EntityManager em) {
        FailureMessage failureMessage = new FailureMessage()
            .failureLevel(DEFAULT_FAILURE_LEVEL)
            .msg(DEFAULT_MSG)
            .parameter(DEFAULT_PARAMETER);
        return failureMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FailureMessage createUpdatedEntity(EntityManager em) {
        FailureMessage failureMessage = new FailureMessage()
            .failureLevel(UPDATED_FAILURE_LEVEL)
            .msg(UPDATED_MSG)
            .parameter(UPDATED_PARAMETER);
        return failureMessage;
    }

    @BeforeEach
    public void initTest() {
        failureMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createFailureMessage() throws Exception {
        int databaseSizeBeforeCreate = failureMessageRepository.findAll().size();

        // Create the FailureMessage
        restFailureMessageMockMvc.perform(post("/api/failure-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(failureMessage)))
            .andExpect(status().isCreated());

        // Validate the FailureMessage in the database
        List<FailureMessage> failureMessageList = failureMessageRepository.findAll();
        assertThat(failureMessageList).hasSize(databaseSizeBeforeCreate + 1);
        FailureMessage testFailureMessage = failureMessageList.get(failureMessageList.size() - 1);
        assertThat(testFailureMessage.getFailureLevel()).isEqualTo(DEFAULT_FAILURE_LEVEL);
        assertThat(testFailureMessage.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testFailureMessage.getParameter()).isEqualTo(DEFAULT_PARAMETER);
    }

    @Test
    @Transactional
    public void createFailureMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = failureMessageRepository.findAll().size();

        // Create the FailureMessage with an existing ID
        failureMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFailureMessageMockMvc.perform(post("/api/failure-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(failureMessage)))
            .andExpect(status().isBadRequest());

        // Validate the FailureMessage in the database
        List<FailureMessage> failureMessageList = failureMessageRepository.findAll();
        assertThat(failureMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFailureMessages() throws Exception {
        // Initialize the database
        failureMessageRepository.saveAndFlush(failureMessage);

        // Get all the failureMessageList
        restFailureMessageMockMvc.perform(get("/api/failure-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(failureMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].failureLevel").value(hasItem(DEFAULT_FAILURE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG)))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)));
    }
    
    @Test
    @Transactional
    public void getFailureMessage() throws Exception {
        // Initialize the database
        failureMessageRepository.saveAndFlush(failureMessage);

        // Get the failureMessage
        restFailureMessageMockMvc.perform(get("/api/failure-messages/{id}", failureMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(failureMessage.getId().intValue()))
            .andExpect(jsonPath("$.failureLevel").value(DEFAULT_FAILURE_LEVEL.toString()))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER));
    }

    @Test
    @Transactional
    public void getNonExistingFailureMessage() throws Exception {
        // Get the failureMessage
        restFailureMessageMockMvc.perform(get("/api/failure-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFailureMessage() throws Exception {
        // Initialize the database
        failureMessageRepository.saveAndFlush(failureMessage);

        int databaseSizeBeforeUpdate = failureMessageRepository.findAll().size();

        // Update the failureMessage
        FailureMessage updatedFailureMessage = failureMessageRepository.findById(failureMessage.getId()).get();
        // Disconnect from session so that the updates on updatedFailureMessage are not directly saved in db
        em.detach(updatedFailureMessage);
        updatedFailureMessage
            .failureLevel(UPDATED_FAILURE_LEVEL)
            .msg(UPDATED_MSG)
            .parameter(UPDATED_PARAMETER);

        restFailureMessageMockMvc.perform(put("/api/failure-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFailureMessage)))
            .andExpect(status().isOk());

        // Validate the FailureMessage in the database
        List<FailureMessage> failureMessageList = failureMessageRepository.findAll();
        assertThat(failureMessageList).hasSize(databaseSizeBeforeUpdate);
        FailureMessage testFailureMessage = failureMessageList.get(failureMessageList.size() - 1);
        assertThat(testFailureMessage.getFailureLevel()).isEqualTo(UPDATED_FAILURE_LEVEL);
        assertThat(testFailureMessage.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testFailureMessage.getParameter()).isEqualTo(UPDATED_PARAMETER);
    }

    @Test
    @Transactional
    public void updateNonExistingFailureMessage() throws Exception {
        int databaseSizeBeforeUpdate = failureMessageRepository.findAll().size();

        // Create the FailureMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFailureMessageMockMvc.perform(put("/api/failure-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(failureMessage)))
            .andExpect(status().isBadRequest());

        // Validate the FailureMessage in the database
        List<FailureMessage> failureMessageList = failureMessageRepository.findAll();
        assertThat(failureMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFailureMessage() throws Exception {
        // Initialize the database
        failureMessageRepository.saveAndFlush(failureMessage);

        int databaseSizeBeforeDelete = failureMessageRepository.findAll().size();

        // Delete the failureMessage
        restFailureMessageMockMvc.perform(delete("/api/failure-messages/{id}", failureMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FailureMessage> failureMessageList = failureMessageRepository.findAll();
        assertThat(failureMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
