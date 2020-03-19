package Softip.Spring.service;

import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.StateRepository;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    @Autowired
    StateMapper stateMapper;

    public List<State> findAllStates(){ return stateRepository.findAll(); }

    public List<StateDTO> findAll(){
        List<State> states = stateRepository.findAll();
        return  states.stream()
                .map(state -> stateMapper.toDto(state))
                .collect(Collectors.toList());
    };

    public State findState(Character c){
       return  stateRepository.findByCharState(c);
    }

    public void initState (){
       State state3= new State('V',"Moved");
       State state2 = new State('M',"Missing");
       State state1 = new State('O',"Ok");
       try {
          stateRepository.save(state1);
          stateRepository.save(state2);
          stateRepository.save(state3);
          }catch (Exception ignored){
       }
    }

    public void add(Character charState, String description) {
        State state = new State(charState, description);
        if (stateRepository.existsByCharState(charState)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "State already exists"
            );
        }
        else {
            stateRepository.save(state);
        }

    }

}
