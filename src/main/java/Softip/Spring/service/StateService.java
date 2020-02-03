package Softip.Spring.service;

import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.repository.PropertyRepositery;
import Softip.Spring.repository.StateRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
    @Autowired
    StateRepositery stateRepositery;

    @Autowired
    StateMapper stateMapper;

    List<StateDTO> findAll (){
        List<State> properties = stateRepositery.findAll();
        return  properties.stream()
                .map(state -> stateMapper.toDto(state))
                .collect(Collectors.toList());
    };
}
