package com.trafalbar.web.rest;
import com.trafalbar.service.OpinionService;
import com.trafalbar.web.rest.errors.BadRequestAlertException;
import com.trafalbar.web.rest.util.HeaderUtil;
import com.trafalbar.service.dto.OpinionDTO;
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
 * REST controller for managing Opinion.
 */
@RestController
@RequestMapping("/api")
public class OpinionResource {

    private final Logger log = LoggerFactory.getLogger(OpinionResource.class);

    private static final String ENTITY_NAME = "opinion";

    private final OpinionService opinionService;

    public OpinionResource(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    /**
     * POST  /opinions : Create a new opinion.
     *
     * @param opinionDTO the opinionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new opinionDTO, or with status 400 (Bad Request) if the opinion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/opinions")
    public ResponseEntity<OpinionDTO> createOpinion(@Valid @RequestBody OpinionDTO opinionDTO) throws URISyntaxException {
        log.debug("REST request to save Opinion : {}", opinionDTO);
        if (opinionDTO.getId() != null) {
            throw new BadRequestAlertException("A new opinion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpinionDTO result = opinionService.save(opinionDTO);
        return ResponseEntity.created(new URI("/api/opinions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /opinions : Updates an existing opinion.
     *
     * @param opinionDTO the opinionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated opinionDTO,
     * or with status 400 (Bad Request) if the opinionDTO is not valid,
     * or with status 500 (Internal Server Error) if the opinionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/opinions")
    public ResponseEntity<OpinionDTO> updateOpinion(@Valid @RequestBody OpinionDTO opinionDTO) throws URISyntaxException {
        log.debug("REST request to update Opinion : {}", opinionDTO);
        if (opinionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpinionDTO result = opinionService.save(opinionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, opinionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /opinions : get all the opinions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of opinions in body
     */
    @GetMapping("/opinions")
    public List<OpinionDTO> getAllOpinions() {
        log.debug("REST request to get all Opinions");
        return opinionService.findAll();
    }

    /**
     * GET  /opinions/:id : get the "id" opinion.
     *
     * @param id the id of the opinionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the opinionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/opinions/{id}")
    public ResponseEntity<OpinionDTO> getOpinion(@PathVariable Long id) {
        log.debug("REST request to get Opinion : {}", id);
        Optional<OpinionDTO> opinionDTO = opinionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opinionDTO);
    }

    /**
     * DELETE  /opinions/:id : delete the "id" opinion.
     *
     * @param id the id of the opinionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/opinions/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable Long id) {
        log.debug("REST request to delete Opinion : {}", id);
        opinionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
