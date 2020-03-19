package Softip.Spring.controller;

import Softip.Spring.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class RoleController {

    @Autowired
   private RoleService roleService;

    @PostConstruct
    void init(){
        roleService.initRole();
    }

}
