package Softip.Spring.repository;

import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  TypeRepository extends JpaRepository<Type,Long> {

    Type findByIntType(int x);

    boolean existsByIntType(int x);
}
