package Softip.Spring.repository;


import Softip.Spring.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepositery extends JpaRepository<Property,Long> {

    //List<Property> findByPropertyStateCharStateOrderByPropertyInDateAsc(char state);

    List<Property> findByPropertyStateCharStateOrderByPropertyPriceDesc(char state);

    List<Property> findByPropertyStateOrderByPropertyPriceAsc(char state);

    List<Property> findByPropertyOutDateNotNull();


}
