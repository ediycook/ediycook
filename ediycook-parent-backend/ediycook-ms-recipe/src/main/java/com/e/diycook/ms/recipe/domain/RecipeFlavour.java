package com.e.diycook.ms.recipe.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A RecipeFlavour.
 */
@Table(name = "recipeFlavour")
public class RecipeFlavour implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String recipeFlavourTypeEnum;

    @Min(value = 0)
    @Max(value = 10)
    private Integer recipeFlavourIntensity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipeFlavourTypeEnum() {
        return recipeFlavourTypeEnum;
    }

    public RecipeFlavour recipeFlavourTypeEnum(String recipeFlavourTypeEnum) {
        this.recipeFlavourTypeEnum = recipeFlavourTypeEnum;
        return this;
    }

    public void setRecipeFlavourTypeEnum(String recipeFlavourTypeEnum) {
        this.recipeFlavourTypeEnum = recipeFlavourTypeEnum;
    }

    public Integer getRecipeFlavourIntensity() {
        return recipeFlavourIntensity;
    }

    public RecipeFlavour recipeFlavourIntensity(Integer recipeFlavourIntensity) {
        this.recipeFlavourIntensity = recipeFlavourIntensity;
        return this;
    }

    public void setRecipeFlavourIntensity(Integer recipeFlavourIntensity) {
        this.recipeFlavourIntensity = recipeFlavourIntensity;
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
        RecipeFlavour recipeFlavour = (RecipeFlavour) o;
        if (recipeFlavour.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeFlavour.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeFlavour{" +
            "id=" + getId() +
            ", recipeFlavourTypeEnum='" + getRecipeFlavourTypeEnum() + "'" +
            ", recipeFlavourIntensity=" + getRecipeFlavourIntensity() +
            "}";
    }
}
