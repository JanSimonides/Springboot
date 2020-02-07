package Softip.Spring.controller;

import Softip.Spring.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostConstruct
    void init(){
        typeService.initType();
    }

}
