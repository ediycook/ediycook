package com.e.diycook.ms.recipe.service.mapper;

import com.e.diycook.ms.recipe.domain.*;
import com.e.diycook.ms.recipe.service.dto.RecipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recipe and its DTO RecipeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RecipeMapper extends EntityMapper<RecipeDTO, Recipe> {


}
