package Softip.Spring.service;


import Softip.Spring.mapper.TypeMapper;
import Softip.Spring.model.dto.TypeDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TypeService {
    @Autowired
    TypeRepository typeRepositery;

    @Autowired
    TypeMapper typeMapper;


    List<TypeDTO> findAll (){
        List<Type> types = typeRepositery.findAll();
        return  types.stream()
                .map(type -> typeMapper.toDto(type))
                .collect(Collectors.toList());
    };


    public Type findType(int x){
        return  typeRepositery.findByIntType(x);
    }

}
