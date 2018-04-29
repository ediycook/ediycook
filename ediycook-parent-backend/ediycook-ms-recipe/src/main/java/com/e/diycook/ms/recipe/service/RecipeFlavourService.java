package com.e.diycook.ms.recipe.service;

import com.e.diycook.ms.recipe.service.dto.RecipeFlavourDTO;
import java.util.List;

/**
 * Service Interface for managing RecipeFlavour.
 */
public interface RecipeFlavourService {

    /**
     * Save a recipeFlavour.
     *
     * @param recipeFlavourDTO the entity to save
     * @return the persisted entity
     */
    RecipeFlavourDTO save(RecipeFlavourDTO recipeFlavourDTO);

    /**
     * Get all the recipeFlavours.
     *
     * @return the list of entities
     */
    List<RecipeFlavourDTO> findAll();

    /**
     * Get the "id" recipeFlavour.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RecipeFlavourDTO findOne(String id);

    /**
     * Delete the "id" recipeFlavour.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
