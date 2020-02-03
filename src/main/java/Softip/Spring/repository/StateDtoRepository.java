package Softip.Spring.repository;

import Softip.Spring.model.dto.StateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDtoRepository extends JpaRepository<StateDTO,Long> {
}
