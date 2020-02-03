package Softip.Spring.repository;

import Softip.Spring.model.dto.StateDTO;
import Softip.Spring.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepositery extends JpaRepository<State,Long> {

    StateDTO findByCharState(Character c);
}
