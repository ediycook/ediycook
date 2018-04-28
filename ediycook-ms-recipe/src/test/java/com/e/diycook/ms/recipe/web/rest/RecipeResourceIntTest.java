package com.e.diycook.ms.recipe.web.rest;

import com.e.diycook.ms.recipe.AbstractCassandraTest;
import com.e.diycook.ms.recipe.EdkmsRecipeApp;

import com.e.diycook.ms.recipe.domain.Recipe;
import com.e.diycook.ms.recipe.repository.RecipeRepository;
import com.e.diycook.ms.recipe.service.RecipeService;
import com.e.diycook.ms.recipe.service.dto.RecipeDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeMapper;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.e.diycook.ms.recipe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecipeResource REST controller.
 *
 * @see RecipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdkmsRecipeApp.class)
public class RecipeResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_RECIPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECIPE_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECIPE_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RECIPE_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECIPE_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_RECIPE_CERTIFIED = false;
    private static final Boolean UPDATED_RECIPE_CERTIFIED = true;

    private static final String DEFAULT_RECIPE_CETIFICATION_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_CETIFICATION_AUTOR = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECIPE_CERTIFICATION_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECIPE_CERTIFICATION_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_RECIPE_SHARED = false;
    private static final Boolean UPDATED_RECIPE_SHARED = true;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeResource recipeResource = new RecipeResource(recipeService);
        this.restRecipeMockMvc = MockMvcBuilders.standaloneSetup(recipeResource)
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
    public static Recipe createEntity() {
        Recipe recipe = new Recipe()
            .recipeName(DEFAULT_RECIPE_NAME)
            .recipeDescription(DEFAULT_RECIPE_DESCRIPTION)
            .recipeCreationDate(DEFAULT_RECIPE_CREATION_DATE)
            .recipeModificationDate(DEFAULT_RECIPE_MODIFICATION_DATE)
            .recipeCertified(DEFAULT_RECIPE_CERTIFIED)
            .recipeCetificationAutor(DEFAULT_RECIPE_CETIFICATION_AUTOR)
            .recipeCertificationStartDate(DEFAULT_RECIPE_CERTIFICATION_START_DATE)
            .recipeShared(DEFAULT_RECIPE_SHARED);
        return recipe;
    }

    @Before
    public void initTest() {
        recipeRepository.deleteAll();
        recipe = createEntity();
    }

    @Test
    public void createRecipe() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getRecipeName()).isEqualTo(DEFAULT_RECIPE_NAME);
        assertThat(testRecipe.getRecipeDescription()).isEqualTo(DEFAULT_RECIPE_DESCRIPTION);
        assertThat(testRecipe.getRecipeCreationDate()).isEqualTo(DEFAULT_RECIPE_CREATION_DATE);
        assertThat(testRecipe.getRecipeModificationDate()).isEqualTo(DEFAULT_RECIPE_MODIFICATION_DATE);
        assertThat(testRecipe.isRecipeCertified()).isEqualTo(DEFAULT_RECIPE_CERTIFIED);
        assertThat(testRecipe.getRecipeCetificationAutor()).isEqualTo(DEFAULT_RECIPE_CETIFICATION_AUTOR);
        assertThat(testRecipe.getRecipeCertificationStartDate()).isEqualTo(DEFAULT_RECIPE_CERTIFICATION_START_DATE);
        assertThat(testRecipe.isRecipeShared()).isEqualTo(DEFAULT_RECIPE_SHARED);
    }

    @Test
    public void createRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // Create the Recipe with an existing ID
        recipe.setId(UUID.randomUUID());
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRecipeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeRepository.findAll().size();
        // set the field null
        recipe.setRecipeName(null);

        // Create the Recipe, which fails.
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRecipeCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipeRepository.findAll().size();
        // set the field null
        recipe.setRecipeCreationDate(null);

        // Create the Recipe, which fails.
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isBadRequest());

        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRecipes() throws Exception {
        // Initialize the database
        recipeRepository.save(recipe);

        // Get all the recipeList
        restRecipeMockMvc.perform(get("/api/recipes"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().toString())))
            .andExpect(jsonPath("$.[*].recipeName").value(hasItem(DEFAULT_RECIPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].recipeDescription").value(hasItem(DEFAULT_RECIPE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].recipeCreationDate").value(hasItem(DEFAULT_RECIPE_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipeModificationDate").value(hasItem(DEFAULT_RECIPE_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipeCertified").value(hasItem(DEFAULT_RECIPE_CERTIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].recipeCetificationAutor").value(hasItem(DEFAULT_RECIPE_CETIFICATION_AUTOR.toString())))
            .andExpect(jsonPath("$.[*].recipeCertificationStartDate").value(hasItem(DEFAULT_RECIPE_CERTIFICATION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipeShared").value(hasItem(DEFAULT_RECIPE_SHARED.booleanValue())));
    }

    @Test
    public void getRecipe() throws Exception {
        // Initialize the database
        recipeRepository.save(recipe);

        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().toString()))
            .andExpect(jsonPath("$.recipeName").value(DEFAULT_RECIPE_NAME.toString()))
            .andExpect(jsonPath("$.recipeDescription").value(DEFAULT_RECIPE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.recipeCreationDate").value(DEFAULT_RECIPE_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.recipeModificationDate").value(DEFAULT_RECIPE_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.recipeCertified").value(DEFAULT_RECIPE_CERTIFIED.booleanValue()))
            .andExpect(jsonPath("$.recipeCetificationAutor").value(DEFAULT_RECIPE_CETIFICATION_AUTOR.toString()))
            .andExpect(jsonPath("$.recipeCertificationStartDate").value(DEFAULT_RECIPE_CERTIFICATION_START_DATE.toString()))
            .andExpect(jsonPath("$.recipeShared").value(DEFAULT_RECIPE_SHARED.booleanValue()));
    }

    @Test
    public void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRecipe() throws Exception {
        // Initialize the database
        recipeRepository.save(recipe);
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findOne(recipe.getId());
        updatedRecipe
            .recipeName(UPDATED_RECIPE_NAME)
            .recipeDescription(UPDATED_RECIPE_DESCRIPTION)
            .recipeCreationDate(UPDATED_RECIPE_CREATION_DATE)
            .recipeModificationDate(UPDATED_RECIPE_MODIFICATION_DATE)
            .recipeCertified(UPDATED_RECIPE_CERTIFIED)
            .recipeCetificationAutor(UPDATED_RECIPE_CETIFICATION_AUTOR)
            .recipeCertificationStartDate(UPDATED_RECIPE_CERTIFICATION_START_DATE)
            .recipeShared(UPDATED_RECIPE_SHARED);
        RecipeDTO recipeDTO = recipeMapper.toDto(updatedRecipe);

        restRecipeMockMvc.perform(put("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getRecipeName()).isEqualTo(UPDATED_RECIPE_NAME);
        assertThat(testRecipe.getRecipeDescription()).isEqualTo(UPDATED_RECIPE_DESCRIPTION);
        assertThat(testRecipe.getRecipeCreationDate()).isEqualTo(UPDATED_RECIPE_CREATION_DATE);
        assertThat(testRecipe.getRecipeModificationDate()).isEqualTo(UPDATED_RECIPE_MODIFICATION_DATE);
        assertThat(testRecipe.isRecipeCertified()).isEqualTo(UPDATED_RECIPE_CERTIFIED);
        assertThat(testRecipe.getRecipeCetificationAutor()).isEqualTo(UPDATED_RECIPE_CETIFICATION_AUTOR);
        assertThat(testRecipe.getRecipeCertificationStartDate()).isEqualTo(UPDATED_RECIPE_CERTIFICATION_START_DATE);
        assertThat(testRecipe.isRecipeShared()).isEqualTo(UPDATED_RECIPE_SHARED);
    }

    @Test
    public void updateNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Create the Recipe
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeMockMvc.perform(put("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeDTO)))
            .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRecipe() throws Exception {
        // Initialize the database
        recipeRepository.save(recipe);
        int databaseSizeBeforeDelete = recipeRepository.findAll().size();

        // Get the recipe
        restRecipeMockMvc.perform(delete("/api/recipes/{id}", recipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipe.class);
        Recipe recipe1 = new Recipe();
        recipe1.setId(UUID.randomUUID());
        Recipe recipe2 = new Recipe();
        recipe2.setId(recipe1.getId());
        assertThat(recipe1).isEqualTo(recipe2);
        recipe2.setId(UUID.randomUUID());
        assertThat(recipe1).isNotEqualTo(recipe2);
        recipe1.setId(null);
        assertThat(recipe1).isNotEqualTo(recipe2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeDTO.class);
        RecipeDTO recipeDTO1 = new RecipeDTO();
        recipeDTO1.setId(UUID.randomUUID());
        RecipeDTO recipeDTO2 = new RecipeDTO();
        assertThat(recipeDTO1).isNotEqualTo(recipeDTO2);
        recipeDTO2.setId(recipeDTO1.getId());
        assertThat(recipeDTO1).isEqualTo(recipeDTO2);
        recipeDTO2.setId(UUID.randomUUID());
        assertThat(recipeDTO1).isNotEqualTo(recipeDTO2);
        recipeDTO1.setId(null);
        assertThat(recipeDTO1).isNotEqualTo(recipeDTO2);
    }
}
