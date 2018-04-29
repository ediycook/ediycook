package com.e.diycook.ms.recipe.web.rest;

import com.e.diycook.ms.recipe.AbstractCassandraTest;
import com.e.diycook.ms.recipe.EdkmsRecipeApp;

import com.e.diycook.ms.recipe.domain.RecipeAppreciation;
import com.e.diycook.ms.recipe.repository.RecipeAppreciationRepository;
import com.e.diycook.ms.recipe.service.RecipeAppreciationService;
import com.e.diycook.ms.recipe.service.dto.RecipeAppreciationDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeAppreciationMapper;
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
 * Test class for the RecipeAppreciationResource REST controller.
 *
 * @see RecipeAppreciationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdkmsRecipeApp.class)
public class RecipeAppreciationResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_RECIPE_APPRECIATION_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_APPRECIATION_AUTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECIPE_APPRECIATION_VOTE_VALUE = 0;
    private static final Integer UPDATED_RECIPE_APPRECIATION_VOTE_VALUE = 1;

    private static final String DEFAULT_RECIPE_APPRECIATION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_APPRECIATION_COMMENT = "BBBBBBBBBB";

    @Autowired
    private RecipeAppreciationRepository recipeAppreciationRepository;

    @Autowired
    private RecipeAppreciationMapper recipeAppreciationMapper;

    @Autowired
    private RecipeAppreciationService recipeAppreciationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRecipeAppreciationMockMvc;

    private RecipeAppreciation recipeAppreciation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeAppreciationResource recipeAppreciationResource = new RecipeAppreciationResource(recipeAppreciationService);
        this.restRecipeAppreciationMockMvc = MockMvcBuilders.standaloneSetup(recipeAppreciationResource)
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
    public static RecipeAppreciation createEntity() {
        RecipeAppreciation recipeAppreciation = new RecipeAppreciation()
            .recipeAppreciationAutor(DEFAULT_RECIPE_APPRECIATION_AUTOR)
            .recipeAppreciationVoteValue(DEFAULT_RECIPE_APPRECIATION_VOTE_VALUE)
            .recipeAppreciationComment(DEFAULT_RECIPE_APPRECIATION_COMMENT);
        return recipeAppreciation;
    }

    @Before
    public void initTest() {
        recipeAppreciationRepository.deleteAll();
        recipeAppreciation = createEntity();
    }

    @Test
    public void createRecipeAppreciation() throws Exception {
        int databaseSizeBeforeCreate = recipeAppreciationRepository.findAll().size();

        // Create the RecipeAppreciation
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationMapper.toDto(recipeAppreciation);
        restRecipeAppreciationMockMvc.perform(post("/api/recipe-appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeAppreciationDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeAppreciation in the database
        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeAppreciation testRecipeAppreciation = recipeAppreciationList.get(recipeAppreciationList.size() - 1);
        assertThat(testRecipeAppreciation.getRecipeAppreciationAutor()).isEqualTo(DEFAULT_RECIPE_APPRECIATION_AUTOR);
        assertThat(testRecipeAppreciation.getRecipeAppreciationVoteValue()).isEqualTo(DEFAULT_RECIPE_APPRECIATION_VOTE_VALUE);
        assertThat(testRecipeAppreciation.getRecipeAppreciationComment()).isEqualTo(DEFAULT_RECIPE_APPRECIATION_COMMENT);
    }

    @Test
    public void createRecipeAppreciationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeAppreciationRepository.findAll().size();

        // Create the RecipeAppreciation with an existing ID
        recipeAppreciation.setId(UUID.randomUUID());
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationMapper.toDto(recipeAppreciation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeAppreciationMockMvc.perform(post("/api/recipe-appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeAppreciationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeAppreciation in the database
        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRecipeAppreciationAutorIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeAppreciationRepository.findAll().size();
        // set the field null
        recipeAppreciation.setRecipeAppreciationAutor(null);

        // Create the RecipeAppreciation, which fails.
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationMapper.toDto(recipeAppreciation);

        restRecipeAppreciationMockMvc.perform(post("/api/recipe-appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeAppreciationDTO)))
            .andExpect(status().isBadRequest());

        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRecipeAppreciations() throws Exception {
        // Initialize the database
        recipeAppreciationRepository.save(recipeAppreciation);

        // Get all the recipeAppreciationList
        restRecipeAppreciationMockMvc.perform(get("/api/recipe-appreciations"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeAppreciation.getId().toString())))
            .andExpect(jsonPath("$.[*].recipeAppreciationAutor").value(hasItem(DEFAULT_RECIPE_APPRECIATION_AUTOR.toString())))
            .andExpect(jsonPath("$.[*].recipeAppreciationVoteValue").value(hasItem(DEFAULT_RECIPE_APPRECIATION_VOTE_VALUE)))
            .andExpect(jsonPath("$.[*].recipeAppreciationComment").value(hasItem(DEFAULT_RECIPE_APPRECIATION_COMMENT.toString())));
    }

    @Test
    public void getRecipeAppreciation() throws Exception {
        // Initialize the database
        recipeAppreciationRepository.save(recipeAppreciation);

        // Get the recipeAppreciation
        restRecipeAppreciationMockMvc.perform(get("/api/recipe-appreciations/{id}", recipeAppreciation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipeAppreciation.getId().toString()))
            .andExpect(jsonPath("$.recipeAppreciationAutor").value(DEFAULT_RECIPE_APPRECIATION_AUTOR.toString()))
            .andExpect(jsonPath("$.recipeAppreciationVoteValue").value(DEFAULT_RECIPE_APPRECIATION_VOTE_VALUE))
            .andExpect(jsonPath("$.recipeAppreciationComment").value(DEFAULT_RECIPE_APPRECIATION_COMMENT.toString()));
    }

    @Test
    public void getNonExistingRecipeAppreciation() throws Exception {
        // Get the recipeAppreciation
        restRecipeAppreciationMockMvc.perform(get("/api/recipe-appreciations/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRecipeAppreciation() throws Exception {
        // Initialize the database
        recipeAppreciationRepository.save(recipeAppreciation);
        int databaseSizeBeforeUpdate = recipeAppreciationRepository.findAll().size();

        // Update the recipeAppreciation
        RecipeAppreciation updatedRecipeAppreciation = recipeAppreciationRepository.findOne(recipeAppreciation.getId());
        updatedRecipeAppreciation
            .recipeAppreciationAutor(UPDATED_RECIPE_APPRECIATION_AUTOR)
            .recipeAppreciationVoteValue(UPDATED_RECIPE_APPRECIATION_VOTE_VALUE)
            .recipeAppreciationComment(UPDATED_RECIPE_APPRECIATION_COMMENT);
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationMapper.toDto(updatedRecipeAppreciation);

        restRecipeAppreciationMockMvc.perform(put("/api/recipe-appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeAppreciationDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeAppreciation in the database
        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeUpdate);
        RecipeAppreciation testRecipeAppreciation = recipeAppreciationList.get(recipeAppreciationList.size() - 1);
        assertThat(testRecipeAppreciation.getRecipeAppreciationAutor()).isEqualTo(UPDATED_RECIPE_APPRECIATION_AUTOR);
        assertThat(testRecipeAppreciation.getRecipeAppreciationVoteValue()).isEqualTo(UPDATED_RECIPE_APPRECIATION_VOTE_VALUE);
        assertThat(testRecipeAppreciation.getRecipeAppreciationComment()).isEqualTo(UPDATED_RECIPE_APPRECIATION_COMMENT);
    }

    @Test
    public void updateNonExistingRecipeAppreciation() throws Exception {
        int databaseSizeBeforeUpdate = recipeAppreciationRepository.findAll().size();

        // Create the RecipeAppreciation
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationMapper.toDto(recipeAppreciation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeAppreciationMockMvc.perform(put("/api/recipe-appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeAppreciationDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeAppreciation in the database
        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRecipeAppreciation() throws Exception {
        // Initialize the database
        recipeAppreciationRepository.save(recipeAppreciation);
        int databaseSizeBeforeDelete = recipeAppreciationRepository.findAll().size();

        // Get the recipeAppreciation
        restRecipeAppreciationMockMvc.perform(delete("/api/recipe-appreciations/{id}", recipeAppreciation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecipeAppreciation> recipeAppreciationList = recipeAppreciationRepository.findAll();
        assertThat(recipeAppreciationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeAppreciation.class);
        RecipeAppreciation recipeAppreciation1 = new RecipeAppreciation();
        recipeAppreciation1.setId(UUID.randomUUID());
        RecipeAppreciation recipeAppreciation2 = new RecipeAppreciation();
        recipeAppreciation2.setId(recipeAppreciation1.getId());
        assertThat(recipeAppreciation1).isEqualTo(recipeAppreciation2);
        recipeAppreciation2.setId(UUID.randomUUID());
        assertThat(recipeAppreciation1).isNotEqualTo(recipeAppreciation2);
        recipeAppreciation1.setId(null);
        assertThat(recipeAppreciation1).isNotEqualTo(recipeAppreciation2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeAppreciationDTO.class);
        RecipeAppreciationDTO recipeAppreciationDTO1 = new RecipeAppreciationDTO();
        recipeAppreciationDTO1.setId(UUID.randomUUID());
        RecipeAppreciationDTO recipeAppreciationDTO2 = new RecipeAppreciationDTO();
        assertThat(recipeAppreciationDTO1).isNotEqualTo(recipeAppreciationDTO2);
        recipeAppreciationDTO2.setId(recipeAppreciationDTO1.getId());
        assertThat(recipeAppreciationDTO1).isEqualTo(recipeAppreciationDTO2);
        recipeAppreciationDTO2.setId(UUID.randomUUID());
        assertThat(recipeAppreciationDTO1).isNotEqualTo(recipeAppreciationDTO2);
        recipeAppreciationDTO1.setId(null);
        assertThat(recipeAppreciationDTO1).isNotEqualTo(recipeAppreciationDTO2);
    }
}
