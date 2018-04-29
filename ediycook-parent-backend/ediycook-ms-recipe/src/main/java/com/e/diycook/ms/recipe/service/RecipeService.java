package com.e.diycook.ms.recipe.service;

import com.e.diycook.ms.recipe.service.dto.RecipeDTO;
import java.util.List;

/**
 * Service Interface for managing Recipe.
 */
public interface RecipeService {

    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save
     * @return the persisted entity
     */
    RecipeDTO save(RecipeDTO recipeDTO);

    /**
     * Get all the recipes.
     *
     * @return the list of entities
     */
    List<RecipeDTO> findAll();

    /**
     * Get the "id" recipe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RecipeDTO findOne(String id);

    /**
     * Delete the "id" recipe.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
