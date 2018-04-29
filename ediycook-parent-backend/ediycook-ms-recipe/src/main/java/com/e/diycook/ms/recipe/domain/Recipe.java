package com.e.diycook.ms.recipe.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A Recipe.
 */
@Table(name = "recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Recipe recipeName(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public Recipe recipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
        return this;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Instant getRecipeCreationDate() {
        return recipeCreationDate;
    }

    public Recipe recipeCreationDate(Instant recipeCreationDate) {
        this.recipeCreationDate = recipeCreationDate;
        return this;
    }

    public void setRecipeCreationDate(Instant recipeCreationDate) {
        this.recipeCreationDate = recipeCreationDate;
    }

    public Instant getRecipeModificationDate() {
        return recipeModificationDate;
    }

    public Recipe recipeModificationDate(Instant recipeModificationDate) {
        this.recipeModificationDate = recipeModificationDate;
        return this;
    }

    public void setRecipeModificationDate(Instant recipeModificationDate) {
        this.recipeModificationDate = recipeModificationDate;
    }

    public Boolean isRecipeCertified() {
        return recipeCertified;
    }

    public Recipe recipeCertified(Boolean recipeCertified) {
        this.recipeCertified = recipeCertified;
        return this;
    }

    public void setRecipeCertified(Boolean recipeCertified) {
        this.recipeCertified = recipeCertified;
    }

    public String getRecipeCetificationAutor() {
        return recipeCetificationAutor;
    }

    public Recipe recipeCetificationAutor(String recipeCetificationAutor) {
        this.recipeCetificationAutor = recipeCetificationAutor;
        return this;
    }

    public void setRecipeCetificationAutor(String recipeCetificationAutor) {
        this.recipeCetificationAutor = recipeCetificationAutor;
    }

    public Instant getRecipeCertificationStartDate() {
        return recipeCertificationStartDate;
    }

    public Recipe recipeCertificationStartDate(Instant recipeCertificationStartDate) {
        this.recipeCertificationStartDate = recipeCertificationStartDate;
        return this;
    }

    public void setRecipeCertificationStartDate(Instant recipeCertificationStartDate) {
        this.recipeCertificationStartDate = recipeCertificationStartDate;
    }

    public Boolean isRecipeShared() {
        return recipeShared;
    }

    public Recipe recipeShared(Boolean recipeShared) {
        this.recipeShared = recipeShared;
        return this;
    }

    public void setRecipeShared(Boolean recipeShared) {
        this.recipeShared = recipeShared;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        if (recipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
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
