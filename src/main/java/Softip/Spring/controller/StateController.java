package Softip.Spring.controller;


import Softip.Spring.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class StateController  {

    @Autowired
    private StateService stateService;

    @PostConstruct
    void init(){
        stateService.initState();
    }
}
