package com.e.diycook.ms.recipe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.e.diycook.ms.recipe.service.RecipeAppreciationService;
import com.e.diycook.ms.recipe.web.rest.errors.BadRequestAlertException;
import com.e.diycook.ms.recipe.web.rest.util.HeaderUtil;
import com.e.diycook.ms.recipe.service.dto.RecipeAppreciationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing RecipeAppreciation.
 */
@RestController
@RequestMapping("/api")
public class RecipeAppreciationResource {

    private final Logger log = LoggerFactory.getLogger(RecipeAppreciationResource.class);

    private static final String ENTITY_NAME = "recipeAppreciation";

    private final RecipeAppreciationService recipeAppreciationService;

    public RecipeAppreciationResource(RecipeAppreciationService recipeAppreciationService) {
        this.recipeAppreciationService = recipeAppreciationService;
    }

    /**
     * POST  /recipe-appreciations : Create a new recipeAppreciation.
     *
     * @param recipeAppreciationDTO the recipeAppreciationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeAppreciationDTO, or with status 400 (Bad Request) if the recipeAppreciation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipe-appreciations")
    @Timed
    public ResponseEntity<RecipeAppreciationDTO> createRecipeAppreciation(@Valid @RequestBody RecipeAppreciationDTO recipeAppreciationDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeAppreciation : {}", recipeAppreciationDTO);
        if (recipeAppreciationDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeAppreciation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeAppreciationDTO result = recipeAppreciationService.save(recipeAppreciationDTO);
        return ResponseEntity.created(new URI("/api/recipe-appreciations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipe-appreciations : Updates an existing recipeAppreciation.
     *
     * @param recipeAppreciationDTO the recipeAppreciationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeAppreciationDTO,
     * or with status 400 (Bad Request) if the recipeAppreciationDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeAppreciationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipe-appreciations")
    @Timed
    public ResponseEntity<RecipeAppreciationDTO> updateRecipeAppreciation(@Valid @RequestBody RecipeAppreciationDTO recipeAppreciationDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeAppreciation : {}", recipeAppreciationDTO);
        if (recipeAppreciationDTO.getId() == null) {
            return createRecipeAppreciation(recipeAppreciationDTO);
        }
        RecipeAppreciationDTO result = recipeAppreciationService.save(recipeAppreciationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeAppreciationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipe-appreciations : get all the recipeAppreciations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recipeAppreciations in body
     */
    @GetMapping("/recipe-appreciations")
    @Timed
    public List<RecipeAppreciationDTO> getAllRecipeAppreciations() {
        log.debug("REST request to get all RecipeAppreciations");
        return recipeAppreciationService.findAll();
        }

    /**
     * GET  /recipe-appreciations/:id : get the "id" recipeAppreciation.
     *
     * @param id the id of the recipeAppreciationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeAppreciationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipe-appreciations/{id}")
    @Timed
    public ResponseEntity<RecipeAppreciationDTO> getRecipeAppreciation(@PathVariable String id) {
        log.debug("REST request to get RecipeAppreciation : {}", id);
        RecipeAppreciationDTO recipeAppreciationDTO = recipeAppreciationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeAppreciationDTO));
    }

    /**
     * DELETE  /recipe-appreciations/:id : delete the "id" recipeAppreciation.
     *
     * @param id the id of the recipeAppreciationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipe-appreciations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipeAppreciation(@PathVariable String id) {
        log.debug("REST request to delete RecipeAppreciation : {}", id);
        recipeAppreciationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
