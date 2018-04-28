package com.e.diycook.ms.brand.service.impl;

import com.e.diycook.ms.brand.service.BrandService;
import com.e.diycook.ms.brand.domain.Brand;
import com.e.diycook.ms.brand.repository.BrandRepository;
import com.e.diycook.ms.brand.service.dto.BrandDTO;
import com.e.diycook.ms.brand.service.mapper.BrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Brand.
 */
@Service
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        log.debug("Request to save Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    /**
     * Get all the brands.
     *
     * @return the list of entities
     */
    @Override
    public List<BrandDTO> findAll() {
        log.debug("Request to get all Brands");
        return brandRepository.findAll().stream()
            .map(brandMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one brand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public BrandDTO findOne(String id) {
        log.debug("Request to get Brand : {}", id);
        Brand brand = brandRepository.findOne(UUID.fromString(id));
        return brandMapper.toDto(brand);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.delete(UUID.fromString(id));
    }
}
