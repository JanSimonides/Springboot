package Softip.Spring.controller;


import Softip.Spring.model.entity.State;
import Softip.Spring.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
public class StateController  {

    @Autowired
    private StateService stateService;

    @PostConstruct
    void init(){
        stateService.initState();
    }

    @GetMapping(path = "/state")
    public List<State> findall() { return stateService.findAllStates() ;
    }

    @PostMapping("/saveState")
    public void create(@RequestBody Map<String, String> body){
         stateService.add(body.get("charState").charAt(0), body.get("description"));

    }
}
