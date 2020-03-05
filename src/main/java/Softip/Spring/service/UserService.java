package Softip.Spring.service;

import Softip.Spring.model.entity.CustomUserDetails;
import Softip.Spring.model.entity.Role;
import Softip.Spring.model.entity.User;
import Softip.Spring.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


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
                    HttpStatus.NOT_FOUND, "username not found"
            );
        }
        String pass = optionalUser.get().getPassword();

       if (password.equals(pass)){
           throw new ResponseStatusException(
                   HttpStatus.OK, "username found"
           );
        }
        else {
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "username not found"
           );
        }

    }

    public void createUser(Map<String, String> body) {
        User user = new User();
        user.setActive(1);
        user.setEmail(body.get("email"));
        user.setUsername(body.get("username"));
        user.setPassword(body.get("password"));

        userRepository.save(user);
    }
}
