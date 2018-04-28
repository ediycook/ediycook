package com.e.diycook.ms.brand.service.mapper;

import com.e.diycook.ms.brand.domain.*;
import com.e.diycook.ms.brand.service.dto.BrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Brand and its DTO BrandDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {


}
