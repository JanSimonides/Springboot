package Softip.Spring.mapper;

import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public abstract class StateMapper  {

   /* @Mappings({
            @Mapping(target = "typBaterie", source = "typBaterie"),
            @Mapping(target = "hodnotaVyroba", source = "hodnotaVyroba"),
            @Mapping(target = "hodnotaDovoz", source = "hodnotaDovoz"),
            @Mapping(target = "hodnotaVyvoz", source = "hodnotaVyvoz"),
            @Mapping(target = "ohlasenieBaterie", ignore = true)
    })*/

    public abstract StateDTO toDto(State entity);

    public abstract State toEntity(StateDTO stateDTO);


}
