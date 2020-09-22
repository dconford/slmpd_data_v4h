package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.SlmpdDataV4HApp;
import org.launchcode.dataviz.domain.CodedDates;
import org.launchcode.dataviz.repository.CodedDatesRepository;
import org.launchcode.dataviz.web.rest.errors.ExceptionTranslator;

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

import static org.launchcode.dataviz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CodedDatesResource} REST controller.
 */
@SpringBootTest(classes = SlmpdDataV4HApp.class)
public class CodedDatesResourceIT {

    private static final String DEFAULT_CODED_DATE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CODED_DATE_STRING = "BBBBBBBBBB";

    @Autowired
    private CodedDatesRepository codedDatesRepository;

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

    private MockMvc restCodedDatesMockMvc;

    private CodedDates codedDates;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CodedDatesResource codedDatesResource = new CodedDatesResource(codedDatesRepository);
        this.restCodedDatesMockMvc = MockMvcBuilders.standaloneSetup(codedDatesResource)
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
    public static CodedDates createEntity(EntityManager em) {
        CodedDates codedDates = new CodedDates()
            .codedDateString(DEFAULT_CODED_DATE_STRING);
        return codedDates;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodedDates createUpdatedEntity(EntityManager em) {
        CodedDates codedDates = new CodedDates()
            .codedDateString(UPDATED_CODED_DATE_STRING);
        return codedDates;
    }

    @BeforeEach
    public void initTest() {
        codedDates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodedDates() throws Exception {
        int databaseSizeBeforeCreate = codedDatesRepository.findAll().size();

        // Create the CodedDates
        restCodedDatesMockMvc.perform(post("/api/coded-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codedDates)))
            .andExpect(status().isCreated());

        // Validate the CodedDates in the database
        List<CodedDates> codedDatesList = codedDatesRepository.findAll();
        assertThat(codedDatesList).hasSize(databaseSizeBeforeCreate + 1);
        CodedDates testCodedDates = codedDatesList.get(codedDatesList.size() - 1);
        assertThat(testCodedDates.getCodedDateString()).isEqualTo(DEFAULT_CODED_DATE_STRING);
    }

    @Test
    @Transactional
    public void createCodedDatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codedDatesRepository.findAll().size();

        // Create the CodedDates with an existing ID
        codedDates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodedDatesMockMvc.perform(post("/api/coded-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codedDates)))
            .andExpect(status().isBadRequest());

        // Validate the CodedDates in the database
        List<CodedDates> codedDatesList = codedDatesRepository.findAll();
        assertThat(codedDatesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCodedDates() throws Exception {
        // Initialize the database
        codedDatesRepository.saveAndFlush(codedDates);

        // Get all the codedDatesList
        restCodedDatesMockMvc.perform(get("/api/coded-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codedDates.getId().intValue())))
            .andExpect(jsonPath("$.[*].codedDateString").value(hasItem(DEFAULT_CODED_DATE_STRING)));
    }
    
    @Test
    @Transactional
    public void getCodedDates() throws Exception {
        // Initialize the database
        codedDatesRepository.saveAndFlush(codedDates);

        // Get the codedDates
        restCodedDatesMockMvc.perform(get("/api/coded-dates/{id}", codedDates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codedDates.getId().intValue()))
            .andExpect(jsonPath("$.codedDateString").value(DEFAULT_CODED_DATE_STRING));
    }

    @Test
    @Transactional
    public void getNonExistingCodedDates() throws Exception {
        // Get the codedDates
        restCodedDatesMockMvc.perform(get("/api/coded-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodedDates() throws Exception {
        // Initialize the database
        codedDatesRepository.saveAndFlush(codedDates);

        int databaseSizeBeforeUpdate = codedDatesRepository.findAll().size();

        // Update the codedDates
        CodedDates updatedCodedDates = codedDatesRepository.findById(codedDates.getId()).get();
        // Disconnect from session so that the updates on updatedCodedDates are not directly saved in db
        em.detach(updatedCodedDates);
        updatedCodedDates
            .codedDateString(UPDATED_CODED_DATE_STRING);

        restCodedDatesMockMvc.perform(put("/api/coded-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCodedDates)))
            .andExpect(status().isOk());

        // Validate the CodedDates in the database
        List<CodedDates> codedDatesList = codedDatesRepository.findAll();
        assertThat(codedDatesList).hasSize(databaseSizeBeforeUpdate);
        CodedDates testCodedDates = codedDatesList.get(codedDatesList.size() - 1);
        assertThat(testCodedDates.getCodedDateString()).isEqualTo(UPDATED_CODED_DATE_STRING);
    }

    @Test
    @Transactional
    public void updateNonExistingCodedDates() throws Exception {
        int databaseSizeBeforeUpdate = codedDatesRepository.findAll().size();

        // Create the CodedDates

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodedDatesMockMvc.perform(put("/api/coded-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codedDates)))
            .andExpect(status().isBadRequest());

        // Validate the CodedDates in the database
        List<CodedDates> codedDatesList = codedDatesRepository.findAll();
        assertThat(codedDatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCodedDates() throws Exception {
        // Initialize the database
        codedDatesRepository.saveAndFlush(codedDates);

        int databaseSizeBeforeDelete = codedDatesRepository.findAll().size();

        // Delete the codedDates
        restCodedDatesMockMvc.perform(delete("/api/coded-dates/{id}", codedDates.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodedDates> codedDatesList = codedDatesRepository.findAll();
        assertThat(codedDatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
