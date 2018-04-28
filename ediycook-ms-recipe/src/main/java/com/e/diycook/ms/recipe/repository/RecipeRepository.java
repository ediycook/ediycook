package com.e.diycook.ms.recipe.repository;

import com.e.diycook.ms.recipe.domain.Recipe;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Recipe entity.
 */
@Repository
public class RecipeRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Recipe> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public RecipeRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Recipe.class);
        this.findAllStmt = session.prepare("SELECT * FROM recipe");
        this.truncateStmt = session.prepare("TRUNCATE recipe");
    }

    public List<Recipe> findAll() {
        List<Recipe> recipesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Recipe recipe = new Recipe();
                recipe.setId(row.getUUID("id"));
                recipe.setRecipeName(row.getString("recipeName"));
                recipe.setRecipeDescription(row.getString("recipeDescription"));
                recipe.setRecipeCreationDate(row.get("recipeCreationDate", Instant.class));
                recipe.setRecipeModificationDate(row.get("recipeModificationDate", Instant.class));
                recipe.setRecipeCertified(row.getBool("recipeCertified"));
                recipe.setRecipeCetificationAutor(row.getString("recipeCetificationAutor"));
                recipe.setRecipeCertificationStartDate(row.get("recipeCertificationStartDate", Instant.class));
                recipe.setRecipeShared(row.getBool("recipeShared"));
                return recipe;
            }
        ).forEach(recipesList::add);
        return recipesList;
    }

    public Recipe findOne(UUID id) {
        return mapper.get(id);
    }

    public Recipe save(Recipe recipe) {
        if (recipe.getId() == null) {
            recipe.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(recipe);
        return recipe;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
