package Softip.Spring.repository;


import Softip.Spring.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Set<Role> findByRole(String role);
}
