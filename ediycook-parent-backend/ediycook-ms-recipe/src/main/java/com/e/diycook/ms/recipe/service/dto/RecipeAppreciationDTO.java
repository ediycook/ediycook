package com.e.diycook.ms.recipe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the RecipeAppreciation entity.
 */
public class RecipeAppreciationDTO implements Serializable {

    private UUID id;

    @NotNull
    private String recipeAppreciationAutor;

    @Min(value = 0)
    @Max(value = 5)
    private Integer recipeAppreciationVoteValue;

    private String recipeAppreciationComment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipeAppreciationAutor() {
        return recipeAppreciationAutor;
    }

    public void setRecipeAppreciationAutor(String recipeAppreciationAutor) {
        this.recipeAppreciationAutor = recipeAppreciationAutor;
    }

    public Integer getRecipeAppreciationVoteValue() {
        return recipeAppreciationVoteValue;
    }

    public void setRecipeAppreciationVoteValue(Integer recipeAppreciationVoteValue) {
        this.recipeAppreciationVoteValue = recipeAppreciationVoteValue;
    }

    public String getRecipeAppreciationComment() {
        return recipeAppreciationComment;
    }

    public void setRecipeAppreciationComment(String recipeAppreciationComment) {
        this.recipeAppreciationComment = recipeAppreciationComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeAppreciationDTO recipeAppreciationDTO = (RecipeAppreciationDTO) o;
        if(recipeAppreciationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeAppreciationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeAppreciationDTO{" +
            "id=" + getId() +
            ", recipeAppreciationAutor='" + getRecipeAppreciationAutor() + "'" +
            ", recipeAppreciationVoteValue=" + getRecipeAppreciationVoteValue() +
            ", recipeAppreciationComment='" + getRecipeAppreciationComment() + "'" +
            "}";
    }
}
