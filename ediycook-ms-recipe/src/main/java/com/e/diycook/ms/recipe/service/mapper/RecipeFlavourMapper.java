package com.e.diycook.ms.recipe.service.mapper;

import com.e.diycook.ms.recipe.domain.*;
import com.e.diycook.ms.recipe.service.dto.RecipeFlavourDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecipeFlavour and its DTO RecipeFlavourDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipeFlavourMapper extends EntityMapper<RecipeFlavourDTO, RecipeFlavour> {


}
