package com.e.diycook.ms.brand.repository;

import com.e.diycook.ms.brand.domain.Brand;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Brand entity.
 */
@Repository
public class BrandRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Brand> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public BrandRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Brand.class);
        this.findAllStmt = session.prepare("SELECT * FROM brand");
        this.truncateStmt = session.prepare("TRUNCATE brand");
    }

    public List<Brand> findAll() {
        List<Brand> brandsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Brand brand = new Brand();
                brand.setId(row.getUUID("id"));
                brand.setBrandName(row.getString("brandName"));
                brand.setBrandDescription(row.getString("brandDescription"));
                return brand;
            }
        ).forEach(brandsList::add);
        return brandsList;
    }

    public Brand findOne(UUID id) {
        return mapper.get(id);
    }

    public Brand save(Brand brand) {
        if (brand.getId() == null) {
            brand.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Brand>> violations = validator.validate(brand);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(brand);
        return brand;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
