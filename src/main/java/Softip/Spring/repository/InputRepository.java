package Softip.Spring.repository;

import Softip.Spring.model.entity.Input;
import Softip.Spring.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends JpaRepository<Input,Long> {
}
