package com.e.diycook.ms.brand.service;

import com.e.diycook.ms.brand.service.dto.BrandDTO;
import java.util.List;

/**
 * Service Interface for managing Brand.
 */
public interface BrandService {

    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save
     * @return the persisted entity
     */
    BrandDTO save(BrandDTO brandDTO);

    /**
     * Get all the brands.
     *
     * @return the list of entities
     */
    List<BrandDTO> findAll();

    /**
     * Get the "id" brand.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BrandDTO findOne(String id);

    /**
     * Delete the "id" brand.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
