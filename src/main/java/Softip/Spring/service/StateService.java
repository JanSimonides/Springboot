package Softip.Spring.service;

import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.State;
import Softip.Spring.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    @Autowired
    StateMapper stateMapper;

    List<StateDTO> findAll (){
        List<State> states = stateRepository.findAll();
        return  states.stream()
                .map(state -> stateMapper.toDto(state))
                .collect(Collectors.toList());
    };

    public State findState(Character c){
       return  stateRepository.findByCharState(c);
    }
}
