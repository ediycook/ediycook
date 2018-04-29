package com.e.diycook.ms.recipe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.e.diycook.ms.recipe.service.RecipeFlavourService;
import com.e.diycook.ms.recipe.web.rest.errors.BadRequestAlertException;
import com.e.diycook.ms.recipe.web.rest.util.HeaderUtil;
import com.e.diycook.ms.recipe.service.dto.RecipeFlavourDTO;
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
 * REST controller for managing RecipeFlavour.
 */
@RestController
@RequestMapping("/api")
public class RecipeFlavourResource {

    private final Logger log = LoggerFactory.getLogger(RecipeFlavourResource.class);

    private static final String ENTITY_NAME = "recipeFlavour";

    private final RecipeFlavourService recipeFlavourService;

    public RecipeFlavourResource(RecipeFlavourService recipeFlavourService) {
        this.recipeFlavourService = recipeFlavourService;
    }

    /**
     * POST  /recipe-flavours : Create a new recipeFlavour.
     *
     * @param recipeFlavourDTO the recipeFlavourDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeFlavourDTO, or with status 400 (Bad Request) if the recipeFlavour has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipe-flavours")
    @Timed
    public ResponseEntity<RecipeFlavourDTO> createRecipeFlavour(@Valid @RequestBody RecipeFlavourDTO recipeFlavourDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeFlavour : {}", recipeFlavourDTO);
        if (recipeFlavourDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeFlavour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeFlavourDTO result = recipeFlavourService.save(recipeFlavourDTO);
        return ResponseEntity.created(new URI("/api/recipe-flavours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipe-flavours : Updates an existing recipeFlavour.
     *
     * @param recipeFlavourDTO the recipeFlavourDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeFlavourDTO,
     * or with status 400 (Bad Request) if the recipeFlavourDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeFlavourDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipe-flavours")
    @Timed
    public ResponseEntity<RecipeFlavourDTO> updateRecipeFlavour(@Valid @RequestBody RecipeFlavourDTO recipeFlavourDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeFlavour : {}", recipeFlavourDTO);
        if (recipeFlavourDTO.getId() == null) {
            return createRecipeFlavour(recipeFlavourDTO);
        }
        RecipeFlavourDTO result = recipeFlavourService.save(recipeFlavourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeFlavourDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipe-flavours : get all the recipeFlavours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recipeFlavours in body
     */
    @GetMapping("/recipe-flavours")
    @Timed
    public List<RecipeFlavourDTO> getAllRecipeFlavours() {
        log.debug("REST request to get all RecipeFlavours");
        return recipeFlavourService.findAll();
        }

    /**
     * GET  /recipe-flavours/:id : get the "id" recipeFlavour.
     *
     * @param id the id of the recipeFlavourDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeFlavourDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipe-flavours/{id}")
    @Timed
    public ResponseEntity<RecipeFlavourDTO> getRecipeFlavour(@PathVariable String id) {
        log.debug("REST request to get RecipeFlavour : {}", id);
        RecipeFlavourDTO recipeFlavourDTO = recipeFlavourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recipeFlavourDTO));
    }

    /**
     * DELETE  /recipe-flavours/:id : delete the "id" recipeFlavour.
     *
     * @param id the id of the recipeFlavourDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipe-flavours/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipeFlavour(@PathVariable String id) {
        log.debug("REST request to delete RecipeFlavour : {}", id);
        recipeFlavourService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
