package com.e.diycook.ms.recipe.service.mapper;

import com.e.diycook.ms.recipe.domain.*;
import com.e.diycook.ms.recipe.service.dto.RecipeAppreciationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecipeAppreciation and its DTO RecipeAppreciationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipeAppreciationMapper extends EntityMapper<RecipeAppreciationDTO, RecipeAppreciation> {


}
