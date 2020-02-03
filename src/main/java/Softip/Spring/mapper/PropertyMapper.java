package Softip.Spring.mapper;

import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PropertyMapper {
    @Autowired
    StateMapper stateMapper;

    public abstract PropertyDTO toDto(Property entity);

    public abstract Property toEntity(PropertyDTO propertyDTO);

    @AfterMapping
    protected void ciselnikDto(@MappingTarget PropertyDTO target, Property source) {
       target.setPropertyStateDTO( stateMapper.toDto(source.getPropertyState()));
    }

}
