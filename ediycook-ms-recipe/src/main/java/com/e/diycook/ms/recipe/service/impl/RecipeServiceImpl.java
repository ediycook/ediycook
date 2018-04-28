package com.e.diycook.ms.recipe.service.impl;

import com.e.diycook.ms.recipe.service.RecipeService;
import com.e.diycook.ms.recipe.domain.Recipe;
import com.e.diycook.ms.recipe.repository.RecipeRepository;
import com.e.diycook.ms.recipe.service.dto.RecipeDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Recipe.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipeDTO save(RecipeDTO recipeDTO) {
        log.debug("Request to save Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    /**
     * Get all the recipes.
     *
     * @return the list of entities
     */
    @Override
    public List<RecipeDTO> findAll() {
        log.debug("Request to get all Recipes");
        return recipeRepository.findAll().stream()
            .map(recipeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recipe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public RecipeDTO findOne(String id) {
        log.debug("Request to get Recipe : {}", id);
        Recipe recipe = recipeRepository.findOne(UUID.fromString(id));
        return recipeMapper.toDto(recipe);
    }

    /**
     * Delete the recipe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Recipe : {}", id);
        recipeRepository.delete(UUID.fromString(id));
    }
}
