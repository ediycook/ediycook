package com.e.diycook.ms.recipe.service.impl;

import com.e.diycook.ms.recipe.service.RecipeFlavourService;
import com.e.diycook.ms.recipe.domain.RecipeFlavour;
import com.e.diycook.ms.recipe.repository.RecipeFlavourRepository;
import com.e.diycook.ms.recipe.service.dto.RecipeFlavourDTO;
import com.e.diycook.ms.recipe.service.mapper.RecipeFlavourMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RecipeFlavour.
 */
@Service
public class RecipeFlavourServiceImpl implements RecipeFlavourService {

    private final Logger log = LoggerFactory.getLogger(RecipeFlavourServiceImpl.class);

    private final RecipeFlavourRepository recipeFlavourRepository;

    private final RecipeFlavourMapper recipeFlavourMapper;

    public RecipeFlavourServiceImpl(RecipeFlavourRepository recipeFlavourRepository, RecipeFlavourMapper recipeFlavourMapper) {
        this.recipeFlavourRepository = recipeFlavourRepository;
        this.recipeFlavourMapper = recipeFlavourMapper;
    }

    /**
     * Save a recipeFlavour.
     *
     * @param recipeFlavourDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecipeFlavourDTO save(RecipeFlavourDTO recipeFlavourDTO) {
        log.debug("Request to save RecipeFlavour : {}", recipeFlavourDTO);
        RecipeFlavour recipeFlavour = recipeFlavourMapper.toEntity(recipeFlavourDTO);
        recipeFlavour = recipeFlavourRepository.save(recipeFlavour);
        return recipeFlavourMapper.toDto(recipeFlavour);
    }

    /**
     * Get all the recipeFlavours.
     *
     * @return the list of entities
     */
    @Override
    public List<RecipeFlavourDTO> findAll() {
        log.debug("Request to get all RecipeFlavours");
        return recipeFlavourRepository.findAll().stream()
            .map(recipeFlavourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one recipeFlavour by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public RecipeFlavourDTO findOne(String id) {
        log.debug("Request to get RecipeFlavour : {}", id);
        RecipeFlavour recipeFlavour = recipeFlavourRepository.findOne(UUID.fromString(id));
        return recipeFlavourMapper.toDto(recipeFlavour);
    }

    /**
     * Delete the recipeFlavour by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete RecipeFlavour : {}", id);
        recipeFlavourRepository.delete(UUID.fromString(id));
    }
}
