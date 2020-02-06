package Softip.Spring.service;


import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.repository.PropertyRepository;
import org.mapstruct.AfterMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyMapper propertyMapper;


    public List<Property> findAllProperties(){
        List<Property> list = null;
        list = propertyRepository.findAll();
        System.out.println(list);
        return list;
    }

    @AfterMapping
    public List<PropertyDTO> findAll(){

        List<Property> properties  = propertyRepository.findAll();
        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }




}
