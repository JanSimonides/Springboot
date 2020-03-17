package Softip.Spring.controller;

import Softip.Spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String helo(){
        return "Hello World";
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/helloAdmin")
    public String securedHello(){
        return "Secured Hello";
    }

    @PostMapping("/logintest")
    public void login(@RequestBody Map<String, String> body){
        userService.login(body);
    }

    @PostMapping("/createUser")
    public void create(@RequestBody Map<String, String> body){
        userService.createUser(body);
    }
}
