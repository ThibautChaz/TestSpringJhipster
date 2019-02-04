package com.trafalbar.service.mapper;

import com.trafalbar.domain.*;
import com.trafalbar.service.dto.BottleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bottle and its DTO BottleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BottleMapper extends EntityMapper<BottleDTO, Bottle> {


    @Mapping(target = "opinions", ignore = true)
    Bottle toEntity(BottleDTO bottleDTO);

    default Bottle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bottle bottle = new Bottle();
        bottle.setId(id);
        return bottle;
    }
}
