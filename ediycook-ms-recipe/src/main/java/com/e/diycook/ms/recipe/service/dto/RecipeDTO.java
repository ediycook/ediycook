package com.e.diycook.ms.recipe.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Recipe entity.
 */
public class RecipeDTO implements Serializable {

    private UUID id;

    @NotNull
    private String recipeName;

    private String recipeDescription;

    @NotNull
    private Instant recipeCreationDate;

    private Instant recipeModificationDate;

    private Boolean recipeCertified;

    private String recipeCetificationAutor;

    private Instant recipeCertificationStartDate;

    private Boolean recipeShared;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Instant getRecipeCreationDate() {
        return recipeCreationDate;
    }

    public void setRecipeCreationDate(Instant recipeCreationDate) {
        this.recipeCreationDate = recipeCreationDate;
    }

    public Instant getRecipeModificationDate() {
        return recipeModificationDate;
    }

    public void setRecipeModificationDate(Instant recipeModificationDate) {
        this.recipeModificationDate = recipeModificationDate;
    }

    public Boolean isRecipeCertified() {
        return recipeCertified;
    }

    public void setRecipeCertified(Boolean recipeCertified) {
        this.recipeCertified = recipeCertified;
    }

    public String getRecipeCetificationAutor() {
        return recipeCetificationAutor;
    }

    public void setRecipeCetificationAutor(String recipeCetificationAutor) {
        this.recipeCetificationAutor = recipeCetificationAutor;
    }

    public Instant getRecipeCertificationStartDate() {
        return recipeCertificationStartDate;
    }

    public void setRecipeCertificationStartDate(Instant recipeCertificationStartDate) {
        this.recipeCertificationStartDate = recipeCertificationStartDate;
    }

    public Boolean isRecipeShared() {
        return recipeShared;
    }

    public void setRecipeShared(Boolean recipeShared) {
        this.recipeShared = recipeShared;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeDTO recipeDTO = (RecipeDTO) o;
        if(recipeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
            "id=" + getId() +
            ", recipeName='" + getRecipeName() + "'" +
            ", recipeDescription='" + getRecipeDescription() + "'" +
            ", recipeCreationDate='" + getRecipeCreationDate() + "'" +
            ", recipeModificationDate='" + getRecipeModificationDate() + "'" +
            ", recipeCertified='" + isRecipeCertified() + "'" +
            ", recipeCetificationAutor='" + getRecipeCetificationAutor() + "'" +
            ", recipeCertificationStartDate='" + getRecipeCertificationStartDate() + "'" +
            ", recipeShared='" + isRecipeShared() + "'" +
            "}";
    }
}
