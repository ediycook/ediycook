package com.e.diycook.ms.recipe.service.impl;

import com.e.diycook.ms.recipe.service.RecipeAppreciationService;
import com.e.diycook.ms.recipe.domain.RecipeAppreciation;
import com.e.diycook.ms.recipe.repository.RecipeAppreciationRepository;
import com.e.diycook.ms.recipe.service.dto.RecipeAppreciationDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeAppreciationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RecipeAppreciation.
 */
@Service
public class RecipeAppreciationServiceImpl implements RecipeAppreciationService {

    private final Logger log = LoggerFactory.getLogger(RecipeAppreciationServiceImpl.class);

    private final RecipeAppreciationRepository recipeAppreciationRepository;

    private final RecipeAppreciationMapper recipeAppreciationMapper;

    public RecipeAppreciationServiceImpl(RecipeAppreciationRepository recipeAppreciationRepository, RecipeAppreciationMapper recipeAppreciationMapper) {
        this.recipeAppreciationRepository = recipeAppreciationRepository;
        this.recipeAppreciationMapper = recipeAppreciationMapper;
    }

    /**
     * Save a recipeAppreciation.
     *
     * @param recipeAppreciationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipeAppreciationDTO save(RecipeAppreciationDTO recipeAppreciationDTO) {
        log.debug("Request to save RecipeAppreciation : {}", recipeAppreciationDTO);
        RecipeAppreciation recipeAppreciation = recipeAppreciationMapper.toEntity(recipeAppreciationDTO);
        recipeAppreciation = recipeAppreciationRepository.save(recipeAppreciation);
        return recipeAppreciationMapper.toDto(recipeAppreciation);
    }

    /**
     * Get all the recipeAppreciations.
     *
     * @return the list of entities
     */
    @Override
    public List<RecipeAppreciationDTO> findAll() {
        log.debug("Request to get all RecipeAppreciations");
        return recipeAppreciationRepository.findAll().stream()
            .map(recipeAppreciationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recipeAppreciation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public RecipeAppreciationDTO findOne(String id) {
        log.debug("Request to get RecipeAppreciation : {}", id);
        RecipeAppreciation recipeAppreciation = recipeAppreciationRepository.findOne(UUID.fromString(id));
        return recipeAppreciationMapper.toDto(recipeAppreciation);
    }

    /**
     * Delete the recipeAppreciation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete RecipeAppreciation : {}", id);
        recipeAppreciationRepository.delete(UUID.fromString(id));
    }
}
