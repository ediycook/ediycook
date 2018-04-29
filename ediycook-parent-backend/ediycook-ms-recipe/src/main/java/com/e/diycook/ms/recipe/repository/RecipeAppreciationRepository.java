package com.e.diycook.ms.recipe.repository;

import com.e.diycook.ms.recipe.domain.RecipeAppreciation;
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
 * Cassandra repository for the RecipeAppreciation entity.
 */
@Repository
public class RecipeAppreciationRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<RecipeAppreciation> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public RecipeAppreciationRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(RecipeAppreciation.class);
        this.findAllStmt = session.prepare("SELECT * FROM recipeAppreciation");
        this.truncateStmt = session.prepare("TRUNCATE recipeAppreciation");
    }

    public List<RecipeAppreciation> findAll() {
        List<RecipeAppreciation> recipeAppreciationsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                RecipeAppreciation recipeAppreciation = new RecipeAppreciation();
                recipeAppreciation.setId(row.getUUID("id"));
                recipeAppreciation.setRecipeAppreciationAutor(row.getString("recipeAppreciationAutor"));
                recipeAppreciation.setRecipeAppreciationVoteValue(row.getInt("recipeAppreciationVoteValue"));
                recipeAppreciation.setRecipeAppreciationComment(row.getString("recipeAppreciationComment"));
                return recipeAppreciation;
            }
        ).forEach(recipeAppreciationsList::add);
        return recipeAppreciationsList;
    }

    public RecipeAppreciation findOne(UUID id) {
        return mapper.get(id);
    }

    public RecipeAppreciation save(RecipeAppreciation recipeAppreciation) {
        if (recipeAppreciation.getId() == null) {
            recipeAppreciation.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<RecipeAppreciation>> violations = validator.validate(recipeAppreciation);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(recipeAppreciation);
        return recipeAppreciation;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
