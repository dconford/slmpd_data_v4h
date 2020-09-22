package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.SlmpdDataV4HApp;
import org.launchcode.dataviz.domain.CrimeCategories;
import org.launchcode.dataviz.repository.CrimeCategoriesRepository;
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
 * Integration tests for the {@link CrimeCategoriesResource} REST controller.
 */
@SpringBootTest(classes = SlmpdDataV4HApp.class)
public class CrimeCategoriesResourceIT {

    private static final String DEFAULT_CRIME_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CRIME_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private CrimeCategoriesRepository crimeCategoriesRepository;

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

    private MockMvc restCrimeCategoriesMockMvc;

    private CrimeCategories crimeCategories;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CrimeCategoriesResource crimeCategoriesResource = new CrimeCategoriesResource(crimeCategoriesRepository);
        this.restCrimeCategoriesMockMvc = MockMvcBuilders.standaloneSetup(crimeCategoriesResource)
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
    public static CrimeCategories createEntity(EntityManager em) {
        CrimeCategories crimeCategories = new CrimeCategories()
            .crimeCategory(DEFAULT_CRIME_CATEGORY);
        return crimeCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrimeCategories createUpdatedEntity(EntityManager em) {
        CrimeCategories crimeCategories = new CrimeCategories()
            .crimeCategory(UPDATED_CRIME_CATEGORY);
        return crimeCategories;
    }

    @BeforeEach
    public void initTest() {
        crimeCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrimeCategories() throws Exception {
        int databaseSizeBeforeCreate = crimeCategoriesRepository.findAll().size();

        // Create the CrimeCategories
        restCrimeCategoriesMockMvc.perform(post("/api/crime-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crimeCategories)))
            .andExpect(status().isCreated());

        // Validate the CrimeCategories in the database
        List<CrimeCategories> crimeCategoriesList = crimeCategoriesRepository.findAll();
        assertThat(crimeCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        CrimeCategories testCrimeCategories = crimeCategoriesList.get(crimeCategoriesList.size() - 1);
        assertThat(testCrimeCategories.getCrimeCategory()).isEqualTo(DEFAULT_CRIME_CATEGORY);
    }

    @Test
    @Transactional
    public void createCrimeCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crimeCategoriesRepository.findAll().size();

        // Create the CrimeCategories with an existing ID
        crimeCategories.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrimeCategoriesMockMvc.perform(post("/api/crime-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crimeCategories)))
            .andExpect(status().isBadRequest());

        // Validate the CrimeCategories in the database
        List<CrimeCategories> crimeCategoriesList = crimeCategoriesRepository.findAll();
        assertThat(crimeCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCrimeCategories() throws Exception {
        // Initialize the database
        crimeCategoriesRepository.saveAndFlush(crimeCategories);

        // Get all the crimeCategoriesList
        restCrimeCategoriesMockMvc.perform(get("/api/crime-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crimeCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].crimeCategory").value(hasItem(DEFAULT_CRIME_CATEGORY)));
    }
    
    @Test
    @Transactional
    public void getCrimeCategories() throws Exception {
        // Initialize the database
        crimeCategoriesRepository.saveAndFlush(crimeCategories);

        // Get the crimeCategories
        restCrimeCategoriesMockMvc.perform(get("/api/crime-categories/{id}", crimeCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crimeCategories.getId().intValue()))
            .andExpect(jsonPath("$.crimeCategory").value(DEFAULT_CRIME_CATEGORY));
    }

    @Test
    @Transactional
    public void getNonExistingCrimeCategories() throws Exception {
        // Get the crimeCategories
        restCrimeCategoriesMockMvc.perform(get("/api/crime-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrimeCategories() throws Exception {
        // Initialize the database
        crimeCategoriesRepository.saveAndFlush(crimeCategories);

        int databaseSizeBeforeUpdate = crimeCategoriesRepository.findAll().size();

        // Update the crimeCategories
        CrimeCategories updatedCrimeCategories = crimeCategoriesRepository.findById(crimeCategories.getId()).get();
        // Disconnect from session so that the updates on updatedCrimeCategories are not directly saved in db
        em.detach(updatedCrimeCategories);
        updatedCrimeCategories
            .crimeCategory(UPDATED_CRIME_CATEGORY);

        restCrimeCategoriesMockMvc.perform(put("/api/crime-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCrimeCategories)))
            .andExpect(status().isOk());

        // Validate the CrimeCategories in the database
        List<CrimeCategories> crimeCategoriesList = crimeCategoriesRepository.findAll();
        assertThat(crimeCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CrimeCategories testCrimeCategories = crimeCategoriesList.get(crimeCategoriesList.size() - 1);
        assertThat(testCrimeCategories.getCrimeCategory()).isEqualTo(UPDATED_CRIME_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingCrimeCategories() throws Exception {
        int databaseSizeBeforeUpdate = crimeCategoriesRepository.findAll().size();

        // Create the CrimeCategories

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrimeCategoriesMockMvc.perform(put("/api/crime-categories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(crimeCategories)))
            .andExpect(status().isBadRequest());

        // Validate the CrimeCategories in the database
        List<CrimeCategories> crimeCategoriesList = crimeCategoriesRepository.findAll();
        assertThat(crimeCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCrimeCategories() throws Exception {
        // Initialize the database
        crimeCategoriesRepository.saveAndFlush(crimeCategories);

        int databaseSizeBeforeDelete = crimeCategoriesRepository.findAll().size();

        // Delete the crimeCategories
        restCrimeCategoriesMockMvc.perform(delete("/api/crime-categories/{id}", crimeCategories.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CrimeCategories> crimeCategoriesList = crimeCategoriesRepository.findAll();
        assertThat(crimeCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
