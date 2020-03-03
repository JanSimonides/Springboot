package Softip.Spring.controller;

import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostConstruct
    void init(){
        typeService.initType();
    }

    @PostMapping("/saveType")
    public void create(@RequestBody Map<String, String> body){
        typeService.add(body.get("intType"), body.get("description"));
    }

}
