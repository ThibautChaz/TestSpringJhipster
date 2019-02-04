package com.trafalbar.web.rest;

import com.trafalbar.TrafalbarApp;

import com.trafalbar.domain.Bottle;
import com.trafalbar.repository.BottleRepository;
import com.trafalbar.service.BottleService;
import com.trafalbar.service.dto.BottleDTO;
import com.trafalbar.service.mapper.BottleMapper;
import com.trafalbar.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.trafalbar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BottleResource REST controller.
 *
 * @see BottleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrafalbarApp.class)
public class BottleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Double DEFAULT_DEGREE = 1D;
    private static final Double UPDATED_DEGREE = 2D;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MOUTH = "AAAAAAAAAA";
    private static final String UPDATED_MOUTH = "BBBBBBBBBB";

    private static final String DEFAULT_NOSE = "AAAAAAAAAA";
    private static final String UPDATED_NOSE = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAW_MATERIAL = "BBBBBBBBBB";

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private BottleMapper bottleMapper;

    @Autowired
    private BottleService bottleService;

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

    private MockMvc restBottleMockMvc;

    private Bottle bottle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BottleResource bottleResource = new BottleResource(bottleService);
        this.restBottleMockMvc = MockMvcBuilders.standaloneSetup(bottleResource)
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
    public static Bottle createEntity(EntityManager em) {
        Bottle bottle = new Bottle()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .age(DEFAULT_AGE)
            .degree(DEFAULT_DEGREE)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .origin(DEFAULT_ORIGIN)
            .mouth(DEFAULT_MOUTH)
            .nose(DEFAULT_NOSE)
            .rawMaterial(DEFAULT_RAW_MATERIAL);
        return bottle;
    }

    @Before
    public void initTest() {
        bottle = createEntity(em);
    }

    @Test
    @Transactional
    public void createBottle() throws Exception {
        int databaseSizeBeforeCreate = bottleRepository.findAll().size();

        // Create the Bottle
        BottleDTO bottleDTO = bottleMapper.toDto(bottle);
        restBottleMockMvc.perform(post("/api/bottles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bottleDTO)))
            .andExpect(status().isCreated());

        // Validate the Bottle in the database
        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeCreate + 1);
        Bottle testBottle = bottleList.get(bottleList.size() - 1);
        assertThat(testBottle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBottle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBottle.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testBottle.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testBottle.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testBottle.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testBottle.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testBottle.getMouth()).isEqualTo(DEFAULT_MOUTH);
        assertThat(testBottle.getNose()).isEqualTo(DEFAULT_NOSE);
        assertThat(testBottle.getRawMaterial()).isEqualTo(DEFAULT_RAW_MATERIAL);
    }

    @Test
    @Transactional
    public void createBottleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bottleRepository.findAll().size();

        // Create the Bottle with an existing ID
        bottle.setId(1L);
        BottleDTO bottleDTO = bottleMapper.toDto(bottle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBottleMockMvc.perform(post("/api/bottles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bottleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bottle in the database
        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bottleRepository.findAll().size();
        // set the field null
        bottle.setName(null);

        // Create the Bottle, which fails.
        BottleDTO bottleDTO = bottleMapper.toDto(bottle);

        restBottleMockMvc.perform(post("/api/bottles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bottleDTO)))
            .andExpect(status().isBadRequest());

        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBottles() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        // Get all the bottleList
        restBottleMockMvc.perform(get("/api/bottles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bottle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.doubleValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].mouth").value(hasItem(DEFAULT_MOUTH.toString())))
            .andExpect(jsonPath("$.[*].nose").value(hasItem(DEFAULT_NOSE.toString())))
            .andExpect(jsonPath("$.[*].rawMaterial").value(hasItem(DEFAULT_RAW_MATERIAL.toString())));
    }
    
    @Test
    @Transactional
    public void getBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        // Get the bottle
        restBottleMockMvc.perform(get("/api/bottles/{id}", bottle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bottle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.doubleValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.mouth").value(DEFAULT_MOUTH.toString()))
            .andExpect(jsonPath("$.nose").value(DEFAULT_NOSE.toString()))
            .andExpect(jsonPath("$.rawMaterial").value(DEFAULT_RAW_MATERIAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBottle() throws Exception {
        // Get the bottle
        restBottleMockMvc.perform(get("/api/bottles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        int databaseSizeBeforeUpdate = bottleRepository.findAll().size();

        // Update the bottle
        Bottle updatedBottle = bottleRepository.findById(bottle.getId()).get();
        // Disconnect from session so that the updates on updatedBottle are not directly saved in db
        em.detach(updatedBottle);
        updatedBottle
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .age(UPDATED_AGE)
            .degree(UPDATED_DEGREE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .origin(UPDATED_ORIGIN)
            .mouth(UPDATED_MOUTH)
            .nose(UPDATED_NOSE)
            .rawMaterial(UPDATED_RAW_MATERIAL);
        BottleDTO bottleDTO = bottleMapper.toDto(updatedBottle);

        restBottleMockMvc.perform(put("/api/bottles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bottleDTO)))
            .andExpect(status().isOk());

        // Validate the Bottle in the database
        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeUpdate);
        Bottle testBottle = bottleList.get(bottleList.size() - 1);
        assertThat(testBottle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBottle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBottle.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testBottle.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testBottle.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testBottle.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testBottle.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testBottle.getMouth()).isEqualTo(UPDATED_MOUTH);
        assertThat(testBottle.getNose()).isEqualTo(UPDATED_NOSE);
        assertThat(testBottle.getRawMaterial()).isEqualTo(UPDATED_RAW_MATERIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingBottle() throws Exception {
        int databaseSizeBeforeUpdate = bottleRepository.findAll().size();

        // Create the Bottle
        BottleDTO bottleDTO = bottleMapper.toDto(bottle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBottleMockMvc.perform(put("/api/bottles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bottleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bottle in the database
        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBottle() throws Exception {
        // Initialize the database
        bottleRepository.saveAndFlush(bottle);

        int databaseSizeBeforeDelete = bottleRepository.findAll().size();

        // Delete the bottle
        restBottleMockMvc.perform(delete("/api/bottles/{id}", bottle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bottle> bottleList = bottleRepository.findAll();
        assertThat(bottleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bottle.class);
        Bottle bottle1 = new Bottle();
        bottle1.setId(1L);
        Bottle bottle2 = new Bottle();
        bottle2.setId(bottle1.getId());
        assertThat(bottle1).isEqualTo(bottle2);
        bottle2.setId(2L);
        assertThat(bottle1).isNotEqualTo(bottle2);
        bottle1.setId(null);
        assertThat(bottle1).isNotEqualTo(bottle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BottleDTO.class);
        BottleDTO bottleDTO1 = new BottleDTO();
        bottleDTO1.setId(1L);
        BottleDTO bottleDTO2 = new BottleDTO();
        assertThat(bottleDTO1).isNotEqualTo(bottleDTO2);
        bottleDTO2.setId(bottleDTO1.getId());
        assertThat(bottleDTO1).isEqualTo(bottleDTO2);
        bottleDTO2.setId(2L);
        assertThat(bottleDTO1).isNotEqualTo(bottleDTO2);
        bottleDTO1.setId(null);
        assertThat(bottleDTO1).isNotEqualTo(bottleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bottleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bottleMapper.fromId(null)).isNull();
    }
}
