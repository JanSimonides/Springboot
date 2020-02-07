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
    TypeRepository typeRepository;

    @Autowired
    TypeMapper typeMapper;


    List<TypeDTO> findAll (){
        List<Type> types = typeRepository.findAll();
        return  types.stream()
                .map(type -> typeMapper.toDto(type))
                .collect(Collectors.toList());
    };


    public Type findType(int x){
        return  typeRepository.findByIntType(x);
    }

    public void initType (){
       Type type0 = new Type(0,"IMA");
       Type type1 = new Type(1,"DIM");
       try {
          typeRepository.save(type0);
          typeRepository.save(type1);
       }catch (Exception ignored){
       }
    }


}
