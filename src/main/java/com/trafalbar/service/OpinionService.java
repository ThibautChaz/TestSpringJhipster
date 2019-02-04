package com.trafalbar.service;

import com.trafalbar.domain.Opinion;
import com.trafalbar.repository.OpinionRepository;
import com.trafalbar.service.dto.OpinionDTO;
import com.trafalbar.service.mapper.OpinionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Opinion.
 */
@Service
@Transactional
public class OpinionService {

    private final Logger log = LoggerFactory.getLogger(OpinionService.class);

    private final OpinionRepository opinionRepository;

    private final OpinionMapper opinionMapper;

    public OpinionService(OpinionRepository opinionRepository, OpinionMapper opinionMapper) {
        this.opinionRepository = opinionRepository;
        this.opinionMapper = opinionMapper;
    }

    /**
     * Save a opinion.
     *
     * @param opinionDTO the entity to save
     * @return the persisted entity
     */
    public OpinionDTO save(OpinionDTO opinionDTO) {
        log.debug("Request to save Opinion : {}", opinionDTO);
        Opinion opinion = opinionMapper.toEntity(opinionDTO);
        opinion = opinionRepository.save(opinion);
        return opinionMapper.toDto(opinion);
    }

    /**
     * Get all the opinions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OpinionDTO> findAll() {
        log.debug("Request to get all Opinions");
        return opinionRepository.findAll().stream()
            .map(opinionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one opinion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OpinionDTO> findOne(Long id) {
        log.debug("Request to get Opinion : {}", id);
        return opinionRepository.findById(id)
            .map(opinionMapper::toDto);
    }

    /**
     * Delete the opinion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Opinion : {}", id);        opinionRepository.deleteById(id);
    }
}
