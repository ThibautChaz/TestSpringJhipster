package com.trafalbar.service;

import com.trafalbar.domain.Bottle;
import com.trafalbar.repository.BottleRepository;
import com.trafalbar.service.dto.BottleDTO;
import com.trafalbar.service.mapper.BottleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Bottle.
 */
@Service
@Transactional
public class BottleService {

    private final Logger log = LoggerFactory.getLogger(BottleService.class);

    private final BottleRepository bottleRepository;

    private final BottleMapper bottleMapper;

    public BottleService(BottleRepository bottleRepository, BottleMapper bottleMapper) {
        this.bottleRepository = bottleRepository;
        this.bottleMapper = bottleMapper;
    }

    /**
     * Save a bottle.
     *
     * @param bottleDTO the entity to save
     * @return the persisted entity
     */
    public BottleDTO save(BottleDTO bottleDTO) {
        log.debug("Request to save Bottle : {}", bottleDTO);
        Bottle bottle = bottleMapper.toEntity(bottleDTO);
        bottle = bottleRepository.save(bottle);
        return bottleMapper.toDto(bottle);
    }

    /**
     * Get all the bottles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BottleDTO> findAll() {
        log.debug("Request to get all Bottles");
        return bottleRepository.findAll().stream()
            .map(bottleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bottle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BottleDTO> findOne(Long id) {
        log.debug("Request to get Bottle : {}", id);
        return bottleRepository.findById(id)
            .map(bottleMapper::toDto);
    }

    /**
     * Delete the bottle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bottle : {}", id);        bottleRepository.deleteById(id);
    }
}
