package com.e.diycook.ms.recipe.repository;

import com.e.diycook.ms.recipe.domain.RecipeFlavour;
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
 * Cassandra repository for the RecipeFlavour entity.
 */
@Repository
public class RecipeFlavourRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<RecipeFlavour> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public RecipeFlavourRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(RecipeFlavour.class);
        this.findAllStmt = session.prepare("SELECT * FROM recipeFlavour");
        this.truncateStmt = session.prepare("TRUNCATE recipeFlavour");
    }

    public List<RecipeFlavour> findAll() {
        List<RecipeFlavour> recipeFlavoursList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                RecipeFlavour recipeFlavour = new RecipeFlavour();
                recipeFlavour.setId(row.getUUID("id"));
                recipeFlavour.setRecipeFlavourTypeEnum(row.getString("recipeFlavourTypeEnum"));
                recipeFlavour.setRecipeFlavourIntensity(row.getInt("recipeFlavourIntensity"));
                return recipeFlavour;
            }
        ).forEach(recipeFlavoursList::add);
        return recipeFlavoursList;
    }

    public RecipeFlavour findOne(UUID id) {
        return mapper.get(id);
    }

    public RecipeFlavour save(RecipeFlavour recipeFlavour) {
        if (recipeFlavour.getId() == null) {
            recipeFlavour.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<RecipeFlavour>> violations = validator.validate(recipeFlavour);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(recipeFlavour);
        return recipeFlavour;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
