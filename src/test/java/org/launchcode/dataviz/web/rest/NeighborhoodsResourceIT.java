package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.SlmpdDataV4HApp;
import org.launchcode.dataviz.domain.Neighborhoods;
import org.launchcode.dataviz.repository.NeighborhoodsRepository;
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
 * Integration tests for the {@link NeighborhoodsResource} REST controller.
 */
@SpringBootTest(classes = SlmpdDataV4HApp.class)
public class NeighborhoodsResourceIT {

    private static final String DEFAULT_NEIGHBORHOOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NEIGHBORHOOD_NAME = "BBBBBBBBBB";

    @Autowired
    private NeighborhoodsRepository neighborhoodsRepository;

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

    private MockMvc restNeighborhoodsMockMvc;

    private Neighborhoods neighborhoods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NeighborhoodsResource neighborhoodsResource = new NeighborhoodsResource(neighborhoodsRepository);
        this.restNeighborhoodsMockMvc = MockMvcBuilders.standaloneSetup(neighborhoodsResource)
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
    public static Neighborhoods createEntity(EntityManager em) {
        Neighborhoods neighborhoods = new Neighborhoods()
            .neighborhoodName(DEFAULT_NEIGHBORHOOD_NAME);
        return neighborhoods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Neighborhoods createUpdatedEntity(EntityManager em) {
        Neighborhoods neighborhoods = new Neighborhoods()
            .neighborhoodName(UPDATED_NEIGHBORHOOD_NAME);
        return neighborhoods;
    }

    @BeforeEach
    public void initTest() {
        neighborhoods = createEntity(em);
    }

    @Test
    @Transactional
    public void createNeighborhoods() throws Exception {
        int databaseSizeBeforeCreate = neighborhoodsRepository.findAll().size();

        // Create the Neighborhoods
        restNeighborhoodsMockMvc.perform(post("/api/neighborhoods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(neighborhoods)))
            .andExpect(status().isCreated());

        // Validate the Neighborhoods in the database
        List<Neighborhoods> neighborhoodsList = neighborhoodsRepository.findAll();
        assertThat(neighborhoodsList).hasSize(databaseSizeBeforeCreate + 1);
        Neighborhoods testNeighborhoods = neighborhoodsList.get(neighborhoodsList.size() - 1);
        assertThat(testNeighborhoods.getNeighborhoodName()).isEqualTo(DEFAULT_NEIGHBORHOOD_NAME);
    }

    @Test
    @Transactional
    public void createNeighborhoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = neighborhoodsRepository.findAll().size();

        // Create the Neighborhoods with an existing ID
        neighborhoods.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNeighborhoodsMockMvc.perform(post("/api/neighborhoods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(neighborhoods)))
            .andExpect(status().isBadRequest());

        // Validate the Neighborhoods in the database
        List<Neighborhoods> neighborhoodsList = neighborhoodsRepository.findAll();
        assertThat(neighborhoodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNeighborhoods() throws Exception {
        // Initialize the database
        neighborhoodsRepository.saveAndFlush(neighborhoods);

        // Get all the neighborhoodsList
        restNeighborhoodsMockMvc.perform(get("/api/neighborhoods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(neighborhoods.getId().intValue())))
            .andExpect(jsonPath("$.[*].neighborhoodName").value(hasItem(DEFAULT_NEIGHBORHOOD_NAME)));
    }
    
    @Test
    @Transactional
    public void getNeighborhoods() throws Exception {
        // Initialize the database
        neighborhoodsRepository.saveAndFlush(neighborhoods);

        // Get the neighborhoods
        restNeighborhoodsMockMvc.perform(get("/api/neighborhoods/{id}", neighborhoods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(neighborhoods.getId().intValue()))
            .andExpect(jsonPath("$.neighborhoodName").value(DEFAULT_NEIGHBORHOOD_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingNeighborhoods() throws Exception {
        // Get the neighborhoods
        restNeighborhoodsMockMvc.perform(get("/api/neighborhoods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNeighborhoods() throws Exception {
        // Initialize the database
        neighborhoodsRepository.saveAndFlush(neighborhoods);

        int databaseSizeBeforeUpdate = neighborhoodsRepository.findAll().size();

        // Update the neighborhoods
        Neighborhoods updatedNeighborhoods = neighborhoodsRepository.findById(neighborhoods.getId()).get();
        // Disconnect from session so that the updates on updatedNeighborhoods are not directly saved in db
        em.detach(updatedNeighborhoods);
        updatedNeighborhoods
            .neighborhoodName(UPDATED_NEIGHBORHOOD_NAME);

        restNeighborhoodsMockMvc.perform(put("/api/neighborhoods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNeighborhoods)))
            .andExpect(status().isOk());

        // Validate the Neighborhoods in the database
        List<Neighborhoods> neighborhoodsList = neighborhoodsRepository.findAll();
        assertThat(neighborhoodsList).hasSize(databaseSizeBeforeUpdate);
        Neighborhoods testNeighborhoods = neighborhoodsList.get(neighborhoodsList.size() - 1);
        assertThat(testNeighborhoods.getNeighborhoodName()).isEqualTo(UPDATED_NEIGHBORHOOD_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNeighborhoods() throws Exception {
        int databaseSizeBeforeUpdate = neighborhoodsRepository.findAll().size();

        // Create the Neighborhoods

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNeighborhoodsMockMvc.perform(put("/api/neighborhoods")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(neighborhoods)))
            .andExpect(status().isBadRequest());

        // Validate the Neighborhoods in the database
        List<Neighborhoods> neighborhoodsList = neighborhoodsRepository.findAll();
        assertThat(neighborhoodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNeighborhoods() throws Exception {
        // Initialize the database
        neighborhoodsRepository.saveAndFlush(neighborhoods);

        int databaseSizeBeforeDelete = neighborhoodsRepository.findAll().size();

        // Delete the neighborhoods
        restNeighborhoodsMockMvc.perform(delete("/api/neighborhoods/{id}", neighborhoods.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Neighborhoods> neighborhoodsList = neighborhoodsRepository.findAll();
        assertThat(neighborhoodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
