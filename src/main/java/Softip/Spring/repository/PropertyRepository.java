package Softip.Spring.repository;


import Softip.Spring.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {

    List<Property> findByPropertyStateCharState(char state);
    List<Property> findByPropertyOutDateNotNull();
    Property findByPropertyId(int x);
    boolean existsByPropertyName(String propertyName);
}
