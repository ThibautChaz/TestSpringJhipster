package com.trafalbar.web.rest;
import com.trafalbar.service.BottleService;
import com.trafalbar.web.rest.errors.BadRequestAlertException;
import com.trafalbar.web.rest.util.HeaderUtil;
import com.trafalbar.service.dto.BottleDTO;
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

/**
 * REST controller for managing Bottle.
 */
@RestController
@RequestMapping("/api")
public class BottleResource {

    private final Logger log = LoggerFactory.getLogger(BottleResource.class);

    private static final String ENTITY_NAME = "bottle";

    private final BottleService bottleService;

    public BottleResource(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    /**
     * POST  /bottles : Create a new bottle.
     *
     * @param bottleDTO the bottleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bottleDTO, or with status 400 (Bad Request) if the bottle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bottles")
    public ResponseEntity<BottleDTO> createBottle(@Valid @RequestBody BottleDTO bottleDTO) throws URISyntaxException {
        log.debug("REST request to save Bottle : {}", bottleDTO);
        if (bottleDTO.getId() != null) {
            throw new BadRequestAlertException("A new bottle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BottleDTO result = bottleService.save(bottleDTO);
        return ResponseEntity.created(new URI("/api/bottles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bottles : Updates an existing bottle.
     *
     * @param bottleDTO the bottleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bottleDTO,
     * or with status 400 (Bad Request) if the bottleDTO is not valid,
     * or with status 500 (Internal Server Error) if the bottleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bottles")
    public ResponseEntity<BottleDTO> updateBottle(@Valid @RequestBody BottleDTO bottleDTO) throws URISyntaxException {
        log.debug("REST request to update Bottle : {}", bottleDTO);
        if (bottleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BottleDTO result = bottleService.save(bottleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bottleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bottles : get all the bottles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bottles in body
     */
    @GetMapping("/bottles")
    public List<BottleDTO> getAllBottles() {
        log.debug("REST request to get all Bottles");
        return bottleService.findAll();
    }

    /**
     * GET  /bottles/:id : get the "id" bottle.
     *
     * @param id the id of the bottleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bottleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bottles/{id}")
    public ResponseEntity<BottleDTO> getBottle(@PathVariable Long id) {
        log.debug("REST request to get Bottle : {}", id);
        Optional<BottleDTO> bottleDTO = bottleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bottleDTO);
    }

    /**
     * DELETE  /bottles/:id : delete the "id" bottle.
     *
     * @param id the id of the bottleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bottles/{id}")
    public ResponseEntity<Void> deleteBottle(@PathVariable Long id) {
        log.debug("REST request to delete Bottle : {}", id);
        bottleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
