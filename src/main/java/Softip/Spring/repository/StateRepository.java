package Softip.Spring.repository;

import Softip.Spring.model.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {

    State findByCharState(Character c);
    Boolean existsByCharState (Character c);


}
