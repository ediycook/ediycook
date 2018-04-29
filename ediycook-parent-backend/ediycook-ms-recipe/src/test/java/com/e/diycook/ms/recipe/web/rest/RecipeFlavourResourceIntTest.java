package com.e.diycook.ms.recipe.web.rest;

import com.e.diycook.ms.recipe.AbstractCassandraTest;
import com.e.diycook.ms.recipe.EdkmsRecipeApp;

import com.e.diycook.ms.recipe.domain.RecipeFlavour;
import com.e.diycook.ms.recipe.repository.RecipeFlavourRepository;
import com.e.diycook.ms.recipe.service.RecipeFlavourService;
import com.e.diycook.ms.recipe.service.dto.RecipeFlavourDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeFlavourMapper;
import com.e.diycook.ms.recipe.web.rest.errors.ExceptionTranslator;

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

import java.util.List;
import java.util.UUID;

import static com.e.diycook.ms.recipe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecipeFlavourResource REST controller.
 *
 * @see RecipeFlavourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdkmsRecipeApp.class)
public class RecipeFlavourResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_RECIPE_FLAVOUR_TYPE_ENUM = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_FLAVOUR_TYPE_ENUM = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECIPE_FLAVOUR_INTENSITY = 0;
    private static final Integer UPDATED_RECIPE_FLAVOUR_INTENSITY = 1;

    @Autowired
    private RecipeFlavourRepository recipeFlavourRepository;

    @Autowired
    private RecipeFlavourMapper recipeFlavourMapper;

    @Autowired
    private RecipeFlavourService recipeFlavourService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRecipeFlavourMockMvc;

    private RecipeFlavour recipeFlavour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeFlavourResource recipeFlavourResource = new RecipeFlavourResource(recipeFlavourService);
        this.restRecipeFlavourMockMvc = MockMvcBuilders.standaloneSetup(recipeFlavourResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeFlavour createEntity() {
        RecipeFlavour recipeFlavour = new RecipeFlavour()
            .recipeFlavourTypeEnum(DEFAULT_RECIPE_FLAVOUR_TYPE_ENUM)
            .recipeFlavourIntensity(DEFAULT_RECIPE_FLAVOUR_INTENSITY);
        return recipeFlavour;
    }

    @Before
    public void initTest() {
        recipeFlavourRepository.deleteAll();
        recipeFlavour = createEntity();
    }

    @Test
    public void createRecipeFlavour() throws Exception {
        int databaseSizeBeforeCreate = recipeFlavourRepository.findAll().size();

        // Create the RecipeFlavour
        RecipeFlavourDTO recipeFlavourDTO = recipeFlavourMapper.toDto(recipeFlavour);
        restRecipeFlavourMockMvc.perform(post("/api/recipe-flavours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeFlavourDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeFlavour in the database
        List<RecipeFlavour> recipeFlavourList = recipeFlavourRepository.findAll();
        assertThat(recipeFlavourList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeFlavour testRecipeFlavour = recipeFlavourList.get(recipeFlavourList.size() - 1);
        assertThat(testRecipeFlavour.getRecipeFlavourTypeEnum()).isEqualTo(DEFAULT_RECIPE_FLAVOUR_TYPE_ENUM);
        assertThat(testRecipeFlavour.getRecipeFlavourIntensity()).isEqualTo(DEFAULT_RECIPE_FLAVOUR_INTENSITY);
    }

    @Test
    public void createRecipeFlavourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeFlavourRepository.findAll().size();

        // Create the RecipeFlavour with an existing ID
        recipeFlavour.setId(UUID.randomUUID());
        RecipeFlavourDTO recipeFlavourDTO = recipeFlavourMapper.toDto(recipeFlavour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeFlavourMockMvc.perform(post("/api/recipe-flavours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeFlavourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeFlavour in the database
        List<RecipeFlavour> recipeFlavourList = recipeFlavourRepository.findAll();
        assertThat(recipeFlavourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRecipeFlavours() throws Exception {
        // Initialize the database
        recipeFlavourRepository.save(recipeFlavour);

        // Get all the recipeFlavourList
        restRecipeFlavourMockMvc.perform(get("/api/recipe-flavours"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeFlavour.getId().toString())))
            .andExpect(jsonPath("$.[*].recipeFlavourTypeEnum").value(hasItem(DEFAULT_RECIPE_FLAVOUR_TYPE_ENUM.toString())))
            .andExpect(jsonPath("$.[*].recipeFlavourIntensity").value(hasItem(DEFAULT_RECIPE_FLAVOUR_INTENSITY)));
    }

    @Test
    public void getRecipeFlavour() throws Exception {
        // Initialize the database
        recipeFlavourRepository.save(recipeFlavour);

        // Get the recipeFlavour
        restRecipeFlavourMockMvc.perform(get("/api/recipe-flavours/{id}", recipeFlavour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipeFlavour.getId().toString()))
            .andExpect(jsonPath("$.recipeFlavourTypeEnum").value(DEFAULT_RECIPE_FLAVOUR_TYPE_ENUM.toString()))
            .andExpect(jsonPath("$.recipeFlavourIntensity").value(DEFAULT_RECIPE_FLAVOUR_INTENSITY));
    }

    @Test
    public void getNonExistingRecipeFlavour() throws Exception {
        // Get the recipeFlavour
        restRecipeFlavourMockMvc.perform(get("/api/recipe-flavours/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRecipeFlavour() throws Exception {
        // Initialize the database
        recipeFlavourRepository.save(recipeFlavour);
        int databaseSizeBeforeUpdate = recipeFlavourRepository.findAll().size();

        // Update the recipeFlavour
        RecipeFlavour updatedRecipeFlavour = recipeFlavourRepository.findOne(recipeFlavour.getId());
        updatedRecipeFlavour
            .recipeFlavourTypeEnum(UPDATED_RECIPE_FLAVOUR_TYPE_ENUM)
            .recipeFlavourIntensity(UPDATED_RECIPE_FLAVOUR_INTENSITY);
        RecipeFlavourDTO recipeFlavourDTO = recipeFlavourMapper.toDto(updatedRecipeFlavour);

        restRecipeFlavourMockMvc.perform(put("/api/recipe-flavours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeFlavourDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeFlavour in the database
        List<RecipeFlavour> recipeFlavourList = recipeFlavourRepository.findAll();
        assertThat(recipeFlavourList).hasSize(databaseSizeBeforeUpdate);
        RecipeFlavour testRecipeFlavour = recipeFlavourList.get(recipeFlavourList.size() - 1);
        assertThat(testRecipeFlavour.getRecipeFlavourTypeEnum()).isEqualTo(UPDATED_RECIPE_FLAVOUR_TYPE_ENUM);
        assertThat(testRecipeFlavour.getRecipeFlavourIntensity()).isEqualTo(UPDATED_RECIPE_FLAVOUR_INTENSITY);
    }

    @Test
    public void updateNonExistingRecipeFlavour() throws Exception {
        int databaseSizeBeforeUpdate = recipeFlavourRepository.findAll().size();

        // Create the RecipeFlavour
        RecipeFlavourDTO recipeFlavourDTO = recipeFlavourMapper.toDto(recipeFlavour);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeFlavourMockMvc.perform(put("/api/recipe-flavours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeFlavourDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeFlavour in the database
        List<RecipeFlavour> recipeFlavourList = recipeFlavourRepository.findAll();
        assertThat(recipeFlavourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRecipeFlavour() throws Exception {
        // Initialize the database
        recipeFlavourRepository.save(recipeFlavour);
        int databaseSizeBeforeDelete = recipeFlavourRepository.findAll().size();

        // Get the recipeFlavour
        restRecipeFlavourMockMvc.perform(delete("/api/recipe-flavours/{id}", recipeFlavour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecipeFlavour> recipeFlavourList = recipeFlavourRepository.findAll();
        assertThat(recipeFlavourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeFlavour.class);
        RecipeFlavour recipeFlavour1 = new RecipeFlavour();
        recipeFlavour1.setId(UUID.randomUUID());
        RecipeFlavour recipeFlavour2 = new RecipeFlavour();
        recipeFlavour2.setId(recipeFlavour1.getId());
        assertThat(recipeFlavour1).isEqualTo(recipeFlavour2);
        recipeFlavour2.setId(UUID.randomUUID());
        assertThat(recipeFlavour1).isNotEqualTo(recipeFlavour2);
        recipeFlavour1.setId(null);
        assertThat(recipeFlavour1).isNotEqualTo(recipeFlavour2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeFlavourDTO.class);
        RecipeFlavourDTO recipeFlavourDTO1 = new RecipeFlavourDTO();
        recipeFlavourDTO1.setId(UUID.randomUUID());
        RecipeFlavourDTO recipeFlavourDTO2 = new RecipeFlavourDTO();
        assertThat(recipeFlavourDTO1).isNotEqualTo(recipeFlavourDTO2);
        recipeFlavourDTO2.setId(recipeFlavourDTO1.getId());
        assertThat(recipeFlavourDTO1).isEqualTo(recipeFlavourDTO2);
        recipeFlavourDTO2.setId(UUID.randomUUID());
        assertThat(recipeFlavourDTO1).isNotEqualTo(recipeFlavourDTO2);
        recipeFlavourDTO1.setId(null);
        assertThat(recipeFlavourDTO1).isNotEqualTo(recipeFlavourDTO2);
    }
}
