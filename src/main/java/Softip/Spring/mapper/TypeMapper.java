package Softip.Spring.mapper;

import Softip.Spring.model.dto.TypeDTO;
import Softip.Spring.model.entity.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public abstract class TypeMapper  {


    public abstract TypeDTO toDto(Type entity);

    public abstract Type toEntity(TypeDTO typeDTO);


}
