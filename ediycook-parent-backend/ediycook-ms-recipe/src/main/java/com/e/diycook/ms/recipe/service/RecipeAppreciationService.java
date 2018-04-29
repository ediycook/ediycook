package com.e.diycook.ms.recipe.service;

import com.e.diycook.ms.recipe.service.dto.RecipeAppreciationDTO;
import java.util.List;

/**
 * Service Interface for managing RecipeAppreciation.
 */
public interface RecipeAppreciationService {

    /**
     * Save a recipeAppreciation.
     *
     * @param recipeAppreciationDTO the entity to save
     * @return the persisted entity
     */
    RecipeAppreciationDTO save(RecipeAppreciationDTO recipeAppreciationDTO);

    /**
     * Get all the recipeAppreciations.
     *
     * @return the list of entities
     */
    List<RecipeAppreciationDTO> findAll();

    /**
     * Get the "id" recipeAppreciation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RecipeAppreciationDTO findOne(String id);

    /**
     * Delete the "id" recipeAppreciation.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
