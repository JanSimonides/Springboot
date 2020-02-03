package Softip.Spring.service;


import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.repository.PropertyRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PropertyService {
    @Autowired
    PropertyRepositery propertyRepositery;
    @Autowired
    PropertyMapper propertyMapper;

    public List<PropertyDTO> findAll(){
        List<Property> properties  = propertyRepositery.findAll();
        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }

}
