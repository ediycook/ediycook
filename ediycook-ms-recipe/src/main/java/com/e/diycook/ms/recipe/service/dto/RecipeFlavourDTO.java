package com.e.diycook.ms.recipe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the RecipeFlavour entity.
 */
public class RecipeFlavourDTO implements Serializable {

    private UUID id;

    private FlavourTypeEnum recipeFlavourTypeEnum;

    @Min(value = 0)
    @Max(value = 10)
    private Integer recipeFlavourIntensity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FlavourTypeEnum getRecipeFlavourTypeEnum() {
        return recipeFlavourTypeEnum;
    }

    public void setRecipeFlavourTypeEnum(FlavourTypeEnum recipeFlavourTypeEnum) {
        this.recipeFlavourTypeEnum = recipeFlavourTypeEnum;
    }

    public Integer getRecipeFlavourIntensity() {
        return recipeFlavourIntensity;
    }

    public void setRecipeFlavourIntensity(Integer recipeFlavourIntensity) {
        this.recipeFlavourIntensity = recipeFlavourIntensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeFlavourDTO recipeFlavourDTO = (RecipeFlavourDTO) o;
        if(recipeFlavourDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeFlavourDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeFlavourDTO{" +
            "id=" + getId() +
            ", recipeFlavourTypeEnum='" + getRecipeFlavourTypeEnum() + "'" +
            ", recipeFlavourIntensity=" + getRecipeFlavourIntensity() +
            "}";
    }
}
