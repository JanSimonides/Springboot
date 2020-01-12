package Softip.Spring;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepositery extends JpaRepository<Property,Long> {

    List<Property> findAll();

    List<Property> findByPropertyStateOrderByPropertyInDateAsc(char state);

    List<Property> findByPropertyStateOrderByPropertyPriceDesc(char state);

    List<Property> findByPropertyStateOrderByPropertyPriceAsc(char state);

    List<Property> findByPropertyOutDateNotNull();


}
