package org.launchcode.dataviz.web.rest;

import org.launchcode.dataviz.SlmpdDataV4HApp;
import org.launchcode.dataviz.domain.ReportedEvents;
import org.launchcode.dataviz.repository.ReportedEventsRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.launchcode.dataviz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReportedEventsResource} REST controller.
 */
@SpringBootTest(classes = SlmpdDataV4HApp.class)
public class ReportedEventsResourceIT {

    private static final String DEFAULT_COMPLAINT_ID = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODED_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_CODED_MONTH = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODED_MONTH_AS_INT = 1;
    private static final Integer UPDATED_CODED_MONTH_AS_INT = 2;

    private static final String DEFAULT_EVENT_OCCURRED = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_OCCURRED = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_CRIMEFLAG = "AAAAAAAAAA";
    private static final String UPDATED_NEW_CRIMEFLAG = "BBBBBBBBBB";

    private static final String DEFAULT_CRIME_UNFOUNDED_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_CRIME_UNFOUNDED_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_ADMINISTRATIVE_ADJUSTMENT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_ADMINISTRATIVE_ADJUSTMENT_FLAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final String DEFAULT_CLEANUP_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_CLEANUP_FLAG = "BBBBBBBBBB";

    private static final Integer DEFAULT_CRIME_CODE = 1;
    private static final Integer UPDATED_CRIME_CODE = 2;

    private static final Integer DEFAULT_DISTRICT_CODE = 1;
    private static final Integer UPDATED_DISTRICT_CODE = 2;

    private static final String DEFAULT_EVENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ILEADS_ADDRESS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ILEADS_ADDRESS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ILEADS_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ILEADS_STREET_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NEIGHBORHOOD_CODE = 1;
    private static final Integer UPDATED_NEIGHBORHOOD_CODE = 2;

    private static final String DEFAULT_EVENT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_LOCATION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_LOCATION_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CAD_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAD_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CAD_STREET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CAD_STREET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_X_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_X_COORDINATES = "BBBBBBBBBB";

    private static final String DEFAULT_Y_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_Y_COORDINATES = "BBBBBBBBBB";

    private static final Integer DEFAULT_CRIME_CATEGORY_SHORTENED = 1;
    private static final Integer UPDATED_CRIME_CATEGORY_SHORTENED = 2;

    private static final LocalDate DEFAULT_NEW_DATE_FIELD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEW_DATE_FIELD = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReportedEventsRepository reportedEventsRepository;

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

    private MockMvc restReportedEventsMockMvc;

    private ReportedEvents reportedEvents;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportedEventsResource reportedEventsResource = new ReportedEventsResource(reportedEventsRepository);
        this.restReportedEventsMockMvc = MockMvcBuilders.standaloneSetup(reportedEventsResource)
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
    public static ReportedEvents createEntity(EntityManager em) {
        ReportedEvents reportedEvents = new ReportedEvents()
            .complaintId(DEFAULT_COMPLAINT_ID)
            .codedMonth(DEFAULT_CODED_MONTH)
            .codedMonthAsInt(DEFAULT_CODED_MONTH_AS_INT)
            .eventOccurred(DEFAULT_EVENT_OCCURRED)
            .newCrimeflag(DEFAULT_NEW_CRIMEFLAG)
            .crimeUnfoundedFlag(DEFAULT_CRIME_UNFOUNDED_FLAG)
            .administrativeAdjustmentFlag(DEFAULT_ADMINISTRATIVE_ADJUSTMENT_FLAG)
            .count(DEFAULT_COUNT)
            .cleanupFlag(DEFAULT_CLEANUP_FLAG)
            .crimeCode(DEFAULT_CRIME_CODE)
            .districtCode(DEFAULT_DISTRICT_CODE)
            .eventDescription(DEFAULT_EVENT_DESCRIPTION)
            .ileadsAddressNumber(DEFAULT_ILEADS_ADDRESS_NUMBER)
            .ileadsStreetName(DEFAULT_ILEADS_STREET_NAME)
            .neighborhoodCode(DEFAULT_NEIGHBORHOOD_CODE)
            .eventLocationName(DEFAULT_EVENT_LOCATION_NAME)
            .eventLocationComment(DEFAULT_EVENT_LOCATION_COMMENT)
            .cadStreetNumber(DEFAULT_CAD_STREET_NUMBER)
            .cadStreetName(DEFAULT_CAD_STREET_NAME)
            .xCoordinates(DEFAULT_X_COORDINATES)
            .yCoordinates(DEFAULT_Y_COORDINATES)
            .crimeCategoryShortened(DEFAULT_CRIME_CATEGORY_SHORTENED)
            .newDateField(DEFAULT_NEW_DATE_FIELD);
        return reportedEvents;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportedEvents createUpdatedEntity(EntityManager em) {
        ReportedEvents reportedEvents = new ReportedEvents()
            .complaintId(UPDATED_COMPLAINT_ID)
            .codedMonth(UPDATED_CODED_MONTH)
            .codedMonthAsInt(UPDATED_CODED_MONTH_AS_INT)
            .eventOccurred(UPDATED_EVENT_OCCURRED)
            .newCrimeflag(UPDATED_NEW_CRIMEFLAG)
            .crimeUnfoundedFlag(UPDATED_CRIME_UNFOUNDED_FLAG)
            .administrativeAdjustmentFlag(UPDATED_ADMINISTRATIVE_ADJUSTMENT_FLAG)
            .count(UPDATED_COUNT)
            .cleanupFlag(UPDATED_CLEANUP_FLAG)
            .crimeCode(UPDATED_CRIME_CODE)
            .districtCode(UPDATED_DISTRICT_CODE)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .ileadsAddressNumber(UPDATED_ILEADS_ADDRESS_NUMBER)
            .ileadsStreetName(UPDATED_ILEADS_STREET_NAME)
            .neighborhoodCode(UPDATED_NEIGHBORHOOD_CODE)
            .eventLocationName(UPDATED_EVENT_LOCATION_NAME)
            .eventLocationComment(UPDATED_EVENT_LOCATION_COMMENT)
            .cadStreetNumber(UPDATED_CAD_STREET_NUMBER)
            .cadStreetName(UPDATED_CAD_STREET_NAME)
            .xCoordinates(UPDATED_X_COORDINATES)
            .yCoordinates(UPDATED_Y_COORDINATES)
            .crimeCategoryShortened(UPDATED_CRIME_CATEGORY_SHORTENED)
            .newDateField(UPDATED_NEW_DATE_FIELD);
        return reportedEvents;
    }

    @BeforeEach
    public void initTest() {
        reportedEvents = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportedEvents() throws Exception {
        int databaseSizeBeforeCreate = reportedEventsRepository.findAll().size();

        // Create the ReportedEvents
        restReportedEventsMockMvc.perform(post("/api/reported-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportedEvents)))
            .andExpect(status().isCreated());

        // Validate the ReportedEvents in the database
        List<ReportedEvents> reportedEventsList = reportedEventsRepository.findAll();
        assertThat(reportedEventsList).hasSize(databaseSizeBeforeCreate + 1);
        ReportedEvents testReportedEvents = reportedEventsList.get(reportedEventsList.size() - 1);
        assertThat(testReportedEvents.getComplaintId()).isEqualTo(DEFAULT_COMPLAINT_ID);
        assertThat(testReportedEvents.getCodedMonth()).isEqualTo(DEFAULT_CODED_MONTH);
        assertThat(testReportedEvents.getCodedMonthAsInt()).isEqualTo(DEFAULT_CODED_MONTH_AS_INT);
        assertThat(testReportedEvents.getEventOccurred()).isEqualTo(DEFAULT_EVENT_OCCURRED);
        assertThat(testReportedEvents.getNewCrimeflag()).isEqualTo(DEFAULT_NEW_CRIMEFLAG);
        assertThat(testReportedEvents.getCrimeUnfoundedFlag()).isEqualTo(DEFAULT_CRIME_UNFOUNDED_FLAG);
        assertThat(testReportedEvents.getAdministrativeAdjustmentFlag()).isEqualTo(DEFAULT_ADMINISTRATIVE_ADJUSTMENT_FLAG);
        assertThat(testReportedEvents.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testReportedEvents.getCleanupFlag()).isEqualTo(DEFAULT_CLEANUP_FLAG);
        assertThat(testReportedEvents.getCrimeCode()).isEqualTo(DEFAULT_CRIME_CODE);
        assertThat(testReportedEvents.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testReportedEvents.getEventDescription()).isEqualTo(DEFAULT_EVENT_DESCRIPTION);
        assertThat(testReportedEvents.getIleadsAddressNumber()).isEqualTo(DEFAULT_ILEADS_ADDRESS_NUMBER);
        assertThat(testReportedEvents.getIleadsStreetName()).isEqualTo(DEFAULT_ILEADS_STREET_NAME);
        assertThat(testReportedEvents.getNeighborhoodCode()).isEqualTo(DEFAULT_NEIGHBORHOOD_CODE);
        assertThat(testReportedEvents.getEventLocationName()).isEqualTo(DEFAULT_EVENT_LOCATION_NAME);
        assertThat(testReportedEvents.getEventLocationComment()).isEqualTo(DEFAULT_EVENT_LOCATION_COMMENT);
        assertThat(testReportedEvents.getCadStreetNumber()).isEqualTo(DEFAULT_CAD_STREET_NUMBER);
        assertThat(testReportedEvents.getCadStreetName()).isEqualTo(DEFAULT_CAD_STREET_NAME);
        assertThat(testReportedEvents.getxCoordinates()).isEqualTo(DEFAULT_X_COORDINATES);
        assertThat(testReportedEvents.getyCoordinates()).isEqualTo(DEFAULT_Y_COORDINATES);
        assertThat(testReportedEvents.getCrimeCategoryShortened()).isEqualTo(DEFAULT_CRIME_CATEGORY_SHORTENED);
        assertThat(testReportedEvents.getNewDateField()).isEqualTo(DEFAULT_NEW_DATE_FIELD);
    }

    @Test
    @Transactional
    public void createReportedEventsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportedEventsRepository.findAll().size();

        // Create the ReportedEvents with an existing ID
        reportedEvents.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportedEventsMockMvc.perform(post("/api/reported-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportedEvents)))
            .andExpect(status().isBadRequest());

        // Validate the ReportedEvents in the database
        List<ReportedEvents> reportedEventsList = reportedEventsRepository.findAll();
        assertThat(reportedEventsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReportedEvents() throws Exception {
        // Initialize the database
        reportedEventsRepository.saveAndFlush(reportedEvents);

        // Get all the reportedEventsList
        restReportedEventsMockMvc.perform(get("/api/reported-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportedEvents.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintId").value(hasItem(DEFAULT_COMPLAINT_ID)))
            .andExpect(jsonPath("$.[*].codedMonth").value(hasItem(DEFAULT_CODED_MONTH)))
            .andExpect(jsonPath("$.[*].codedMonthAsInt").value(hasItem(DEFAULT_CODED_MONTH_AS_INT)))
            .andExpect(jsonPath("$.[*].eventOccurred").value(hasItem(DEFAULT_EVENT_OCCURRED)))
            .andExpect(jsonPath("$.[*].newCrimeflag").value(hasItem(DEFAULT_NEW_CRIMEFLAG)))
            .andExpect(jsonPath("$.[*].crimeUnfoundedFlag").value(hasItem(DEFAULT_CRIME_UNFOUNDED_FLAG)))
            .andExpect(jsonPath("$.[*].administrativeAdjustmentFlag").value(hasItem(DEFAULT_ADMINISTRATIVE_ADJUSTMENT_FLAG)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].cleanupFlag").value(hasItem(DEFAULT_CLEANUP_FLAG)))
            .andExpect(jsonPath("$.[*].crimeCode").value(hasItem(DEFAULT_CRIME_CODE)))
            .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
            .andExpect(jsonPath("$.[*].eventDescription").value(hasItem(DEFAULT_EVENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ileadsAddressNumber").value(hasItem(DEFAULT_ILEADS_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].ileadsStreetName").value(hasItem(DEFAULT_ILEADS_STREET_NAME)))
            .andExpect(jsonPath("$.[*].neighborhoodCode").value(hasItem(DEFAULT_NEIGHBORHOOD_CODE)))
            .andExpect(jsonPath("$.[*].eventLocationName").value(hasItem(DEFAULT_EVENT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].eventLocationComment").value(hasItem(DEFAULT_EVENT_LOCATION_COMMENT)))
            .andExpect(jsonPath("$.[*].cadStreetNumber").value(hasItem(DEFAULT_CAD_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].cadStreetName").value(hasItem(DEFAULT_CAD_STREET_NAME)))
            .andExpect(jsonPath("$.[*].xCoordinates").value(hasItem(DEFAULT_X_COORDINATES)))
            .andExpect(jsonPath("$.[*].yCoordinates").value(hasItem(DEFAULT_Y_COORDINATES)))
            .andExpect(jsonPath("$.[*].crimeCategoryShortened").value(hasItem(DEFAULT_CRIME_CATEGORY_SHORTENED)))
            .andExpect(jsonPath("$.[*].newDateField").value(hasItem(DEFAULT_NEW_DATE_FIELD.toString())));
    }
    
    @Test
    @Transactional
    public void getReportedEvents() throws Exception {
        // Initialize the database
        reportedEventsRepository.saveAndFlush(reportedEvents);

        // Get the reportedEvents
        restReportedEventsMockMvc.perform(get("/api/reported-events/{id}", reportedEvents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportedEvents.getId().intValue()))
            .andExpect(jsonPath("$.complaintId").value(DEFAULT_COMPLAINT_ID))
            .andExpect(jsonPath("$.codedMonth").value(DEFAULT_CODED_MONTH))
            .andExpect(jsonPath("$.codedMonthAsInt").value(DEFAULT_CODED_MONTH_AS_INT))
            .andExpect(jsonPath("$.eventOccurred").value(DEFAULT_EVENT_OCCURRED))
            .andExpect(jsonPath("$.newCrimeflag").value(DEFAULT_NEW_CRIMEFLAG))
            .andExpect(jsonPath("$.crimeUnfoundedFlag").value(DEFAULT_CRIME_UNFOUNDED_FLAG))
            .andExpect(jsonPath("$.administrativeAdjustmentFlag").value(DEFAULT_ADMINISTRATIVE_ADJUSTMENT_FLAG))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.cleanupFlag").value(DEFAULT_CLEANUP_FLAG))
            .andExpect(jsonPath("$.crimeCode").value(DEFAULT_CRIME_CODE))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.eventDescription").value(DEFAULT_EVENT_DESCRIPTION))
            .andExpect(jsonPath("$.ileadsAddressNumber").value(DEFAULT_ILEADS_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.ileadsStreetName").value(DEFAULT_ILEADS_STREET_NAME))
            .andExpect(jsonPath("$.neighborhoodCode").value(DEFAULT_NEIGHBORHOOD_CODE))
            .andExpect(jsonPath("$.eventLocationName").value(DEFAULT_EVENT_LOCATION_NAME))
            .andExpect(jsonPath("$.eventLocationComment").value(DEFAULT_EVENT_LOCATION_COMMENT))
            .andExpect(jsonPath("$.cadStreetNumber").value(DEFAULT_CAD_STREET_NUMBER))
            .andExpect(jsonPath("$.cadStreetName").value(DEFAULT_CAD_STREET_NAME))
            .andExpect(jsonPath("$.xCoordinates").value(DEFAULT_X_COORDINATES))
            .andExpect(jsonPath("$.yCoordinates").value(DEFAULT_Y_COORDINATES))
            .andExpect(jsonPath("$.crimeCategoryShortened").value(DEFAULT_CRIME_CATEGORY_SHORTENED))
            .andExpect(jsonPath("$.newDateField").value(DEFAULT_NEW_DATE_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReportedEvents() throws Exception {
        // Get the reportedEvents
        restReportedEventsMockMvc.perform(get("/api/reported-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportedEvents() throws Exception {
        // Initialize the database
        reportedEventsRepository.saveAndFlush(reportedEvents);

        int databaseSizeBeforeUpdate = reportedEventsRepository.findAll().size();

        // Update the reportedEvents
        ReportedEvents updatedReportedEvents = reportedEventsRepository.findById(reportedEvents.getId()).get();
        // Disconnect from session so that the updates on updatedReportedEvents are not directly saved in db
        em.detach(updatedReportedEvents);
        updatedReportedEvents
            .complaintId(UPDATED_COMPLAINT_ID)
            .codedMonth(UPDATED_CODED_MONTH)
            .codedMonthAsInt(UPDATED_CODED_MONTH_AS_INT)
            .eventOccurred(UPDATED_EVENT_OCCURRED)
            .newCrimeflag(UPDATED_NEW_CRIMEFLAG)
            .crimeUnfoundedFlag(UPDATED_CRIME_UNFOUNDED_FLAG)
            .administrativeAdjustmentFlag(UPDATED_ADMINISTRATIVE_ADJUSTMENT_FLAG)
            .count(UPDATED_COUNT)
            .cleanupFlag(UPDATED_CLEANUP_FLAG)
            .crimeCode(UPDATED_CRIME_CODE)
            .districtCode(UPDATED_DISTRICT_CODE)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .ileadsAddressNumber(UPDATED_ILEADS_ADDRESS_NUMBER)
            .ileadsStreetName(UPDATED_ILEADS_STREET_NAME)
            .neighborhoodCode(UPDATED_NEIGHBORHOOD_CODE)
            .eventLocationName(UPDATED_EVENT_LOCATION_NAME)
            .eventLocationComment(UPDATED_EVENT_LOCATION_COMMENT)
            .cadStreetNumber(UPDATED_CAD_STREET_NUMBER)
            .cadStreetName(UPDATED_CAD_STREET_NAME)
            .xCoordinates(UPDATED_X_COORDINATES)
            .yCoordinates(UPDATED_Y_COORDINATES)
            .crimeCategoryShortened(UPDATED_CRIME_CATEGORY_SHORTENED)
            .newDateField(UPDATED_NEW_DATE_FIELD);

        restReportedEventsMockMvc.perform(put("/api/reported-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReportedEvents)))
            .andExpect(status().isOk());

        // Validate the ReportedEvents in the database
        List<ReportedEvents> reportedEventsList = reportedEventsRepository.findAll();
        assertThat(reportedEventsList).hasSize(databaseSizeBeforeUpdate);
        ReportedEvents testReportedEvents = reportedEventsList.get(reportedEventsList.size() - 1);
        assertThat(testReportedEvents.getComplaintId()).isEqualTo(UPDATED_COMPLAINT_ID);
        assertThat(testReportedEvents.getCodedMonth()).isEqualTo(UPDATED_CODED_MONTH);
        assertThat(testReportedEvents.getCodedMonthAsInt()).isEqualTo(UPDATED_CODED_MONTH_AS_INT);
        assertThat(testReportedEvents.getEventOccurred()).isEqualTo(UPDATED_EVENT_OCCURRED);
        assertThat(testReportedEvents.getNewCrimeflag()).isEqualTo(UPDATED_NEW_CRIMEFLAG);
        assertThat(testReportedEvents.getCrimeUnfoundedFlag()).isEqualTo(UPDATED_CRIME_UNFOUNDED_FLAG);
        assertThat(testReportedEvents.getAdministrativeAdjustmentFlag()).isEqualTo(UPDATED_ADMINISTRATIVE_ADJUSTMENT_FLAG);
        assertThat(testReportedEvents.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testReportedEvents.getCleanupFlag()).isEqualTo(UPDATED_CLEANUP_FLAG);
        assertThat(testReportedEvents.getCrimeCode()).isEqualTo(UPDATED_CRIME_CODE);
        assertThat(testReportedEvents.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testReportedEvents.getEventDescription()).isEqualTo(UPDATED_EVENT_DESCRIPTION);
        assertThat(testReportedEvents.getIleadsAddressNumber()).isEqualTo(UPDATED_ILEADS_ADDRESS_NUMBER);
        assertThat(testReportedEvents.getIleadsStreetName()).isEqualTo(UPDATED_ILEADS_STREET_NAME);
        assertThat(testReportedEvents.getNeighborhoodCode()).isEqualTo(UPDATED_NEIGHBORHOOD_CODE);
        assertThat(testReportedEvents.getEventLocationName()).isEqualTo(UPDATED_EVENT_LOCATION_NAME);
        assertThat(testReportedEvents.getEventLocationComment()).isEqualTo(UPDATED_EVENT_LOCATION_COMMENT);
        assertThat(testReportedEvents.getCadStreetNumber()).isEqualTo(UPDATED_CAD_STREET_NUMBER);
        assertThat(testReportedEvents.getCadStreetName()).isEqualTo(UPDATED_CAD_STREET_NAME);
        assertThat(testReportedEvents.getxCoordinates()).isEqualTo(UPDATED_X_COORDINATES);
        assertThat(testReportedEvents.getyCoordinates()).isEqualTo(UPDATED_Y_COORDINATES);
        assertThat(testReportedEvents.getCrimeCategoryShortened()).isEqualTo(UPDATED_CRIME_CATEGORY_SHORTENED);
        assertThat(testReportedEvents.getNewDateField()).isEqualTo(UPDATED_NEW_DATE_FIELD);
    }

    @Test
    @Transactional
    public void updateNonExistingReportedEvents() throws Exception {
        int databaseSizeBeforeUpdate = reportedEventsRepository.findAll().size();

        // Create the ReportedEvents

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportedEventsMockMvc.perform(put("/api/reported-events")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reportedEvents)))
            .andExpect(status().isBadRequest());

        // Validate the ReportedEvents in the database
        List<ReportedEvents> reportedEventsList = reportedEventsRepository.findAll();
        assertThat(reportedEventsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportedEvents() throws Exception {
        // Initialize the database
        reportedEventsRepository.saveAndFlush(reportedEvents);

        int databaseSizeBeforeDelete = reportedEventsRepository.findAll().size();

        // Delete the reportedEvents
        restReportedEventsMockMvc.perform(delete("/api/reported-events/{id}", reportedEvents.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportedEvents> reportedEventsList = reportedEventsRepository.findAll();
        assertThat(reportedEventsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
