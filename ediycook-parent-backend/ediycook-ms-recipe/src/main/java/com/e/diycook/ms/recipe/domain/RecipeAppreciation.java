package com.e.diycook.ms.recipe.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A RecipeAppreciation.
 */
@Table(name = "recipeAppreciation")
public class RecipeAppreciation implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private String recipeAppreciationAutor;

    @Min(value = 0)
    @Max(value = 5)
    private Integer recipeAppreciationVoteValue;

    private String recipeAppreciationComment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipeAppreciationAutor() {
        return recipeAppreciationAutor;
    }

    public RecipeAppreciation recipeAppreciationAutor(String recipeAppreciationAutor) {
        this.recipeAppreciationAutor = recipeAppreciationAutor;
        return this;
    }

    public void setRecipeAppreciationAutor(String recipeAppreciationAutor) {
        this.recipeAppreciationAutor = recipeAppreciationAutor;
    }

    public Integer getRecipeAppreciationVoteValue() {
        return recipeAppreciationVoteValue;
    }

    public RecipeAppreciation recipeAppreciationVoteValue(Integer recipeAppreciationVoteValue) {
        this.recipeAppreciationVoteValue = recipeAppreciationVoteValue;
        return this;
    }

    public void setRecipeAppreciationVoteValue(Integer recipeAppreciationVoteValue) {
        this.recipeAppreciationVoteValue = recipeAppreciationVoteValue;
    }

    public String getRecipeAppreciationComment() {
        return recipeAppreciationComment;
    }

    public RecipeAppreciation recipeAppreciationComment(String recipeAppreciationComment) {
        this.recipeAppreciationComment = recipeAppreciationComment;
        return this;
    }

    public void setRecipeAppreciationComment(String recipeAppreciationComment) {
        this.recipeAppreciationComment = recipeAppreciationComment;
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
        RecipeAppreciation recipeAppreciation = (RecipeAppreciation) o;
        if (recipeAppreciation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeAppreciation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeAppreciation{" +
            "id=" + getId() +
            ", recipeAppreciationAutor='" + getRecipeAppreciationAutor() + "'" +
            ", recipeAppreciationVoteValue=" + getRecipeAppreciationVoteValue() +
            ", recipeAppreciationComment='" + getRecipeAppreciationComment() + "'" +
            "}";
    }
}
