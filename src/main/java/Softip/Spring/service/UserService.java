package Softip.Spring.service;

import Softip.Spring.model.entity.CustomUserDetails;

import Softip.Spring.model.entity.Role;
import Softip.Spring.model.entity.User;
import Softip.Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLDataException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return optionalUser
                .map(CustomUserDetails::new).get();
    }

    public void login(Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (!optionalUser.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Username not found"
            );
        }
        String pass = optionalUser.get().getPassword();

       if (password.equals(pass)){
           throw new ResponseStatusException(
                   HttpStatus.OK, "Login was successful"
           );
        }
        else {
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "Username or password is wrong"
           );
        }

    }

    public void createUser(Map<String, String> body) {
        User user = new User();
        user.setActive(1);
        user.setEmail(body.get("email"));
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));
        user.setRoles(roleService.findRole("user"));


        if (userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Email is using"
            );
        }
        else if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Username is using"
            );
        }
        else {
            userRepository.save(user);
            throw new ResponseStatusException(
                    HttpStatus.OK, "Registration was successful"
            );
        }
    }

    public void createAdmin() {
        User admin = new User();
        admin.setActive(1);
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin1");
        admin.setRoles(roleService.findRole("admin"));

        try {
            userRepository.save(admin);
        }
        catch (Exception ignored){
        }

    }


}
