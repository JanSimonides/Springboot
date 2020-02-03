package Softip.Spring.repository;

import Softip.Spring.model.dto.PropertyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PropertyDtoRepositery extends JpaRepository<PropertyDTO,Long> {
}
