package Softip.Spring.mapper;


import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = TypeMapper.class)
public abstract class PropertyMapper {
    @Autowired
    StateMapper stateMapper;
    @Autowired
    TypeMapper typeMapper;

    public abstract PropertyDTO toDto(Property entity);

    public abstract Property toEntity(PropertyDTO propertyDTO);

    @AfterMapping
    protected void afterDtoToEntity(@MappingTarget PropertyDTO target, Property source) {
        target.setPropertyStateDTO(stateMapper.toDto(source.getPropertyState()));
        target.setPropertyTypeDTO(typeMapper.toDto(source.getPropertyType()));
    }

    @AfterMapping
    protected void afterEntityToDTO(@MappingTarget Property target, PropertyDTO source) {
        target.setPropertyState(stateMapper.toEntity(source.getPropertyStateDTO()));
        target.setPropertyType(typeMapper.toEntity(source.getPropertyTypeDTO()));
    }

}
