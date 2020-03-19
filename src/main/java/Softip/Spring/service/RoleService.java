package Softip.Spring.service;

import Softip.Spring.model.entity.Role;
import Softip.Spring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void initRole() {
        Role admin = new Role("admin");
        Role user = new Role("user");
        try {
            roleRepository.save(admin);
            roleRepository.save(user);
        } catch (Exception ignored) {
        }
    }

    public Set<Role> findRole (String role){
        return  roleRepository.findByRole(role);
    }

}
