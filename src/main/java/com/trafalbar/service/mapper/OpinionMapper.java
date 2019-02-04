package com.trafalbar.service.mapper;

import com.trafalbar.domain.*;
import com.trafalbar.service.dto.OpinionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Opinion and its DTO OpinionDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, BottleMapper.class})
public interface OpinionMapper extends EntityMapper<OpinionDTO, Opinion> {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "bottle.id", target = "bottleId")
    OpinionDTO toDto(Opinion opinion);

    @Mapping(source = "authorId", target = "author")
    @Mapping(source = "bottleId", target = "bottle")
    Opinion toEntity(OpinionDTO opinionDTO);

    default Opinion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Opinion opinion = new Opinion();
        opinion.setId(id);
        return opinion;
    }
}
