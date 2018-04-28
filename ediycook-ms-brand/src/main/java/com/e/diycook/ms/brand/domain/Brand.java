package com.e.diycook.ms.brand.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Brand.
 */
@Table(name = "brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private String brandName;

    private String brandDescription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public Brand brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public Brand brandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
        return this;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
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
        Brand brand = (Brand) o;
        if (brand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Brand{" +
            "id=" + getId() +
            ", brandName='" + getBrandName() + "'" +
            ", brandDescription='" + getBrandDescription() + "'" +
            "}";
    }
}
