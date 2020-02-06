package Softip.Spring.service;


import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.repository.PropertyRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.hibernate.Hibernate;
import org.mapstruct.AfterMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyMapper propertyMapper;

    /*@Transactional
    public List<Property> findAllProperties(){
        return  propertyRepository.findVsetko();
    }*/

    @AfterMapping
    public List<PropertyDTO> findAll(){
        List<Property> properties  = propertyRepository.findAll();

        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }

    @Transactional
    public  List<Property> vsetko(){
        return propertyRepository.findAll();
    }

}
