package Softip.Spring.controller;

import Softip.Spring.YamlCfg;
import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.PropertyRepository;
import Softip.Spring.repository.StateRepository;
import Softip.Spring.repository.TypeRepository;
import Softip.Spring.service.PropertyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
public class PropertyController {

    private final static Logger logger = Logger.getLogger(PropertyController.class);

    @Autowired
    private
    PropertyService propertyService;

    @Autowired
    ApplicationArguments appArgs;

    @Autowired
    private YamlCfg yamlCfg;

    @PostConstruct

    @GetMapping("/")
    public  ModelAndView printDir () {
        ModelAndView model = new ModelAndView("directory.html");
        model.addObject("dir", yamlCfg.getDirectory());
        return  model;
    }


    @GetMapping(path="/all")
    public List<Property> findall() {
        return propertyService.findProperty() ;
    }

    @GetMapping(path ="/model" )
    public List<Property> findProperty(){return propertyService.findProperty();}

    @GetMapping("/ok")
    public  List<Property> findOk() {
        return propertyService.findByChar('O');
    }

    @GetMapping("/moved")
    public  List<Property> findMoved() {
        return propertyService.findByChar('V');
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/missing")
    public  List<Property> findMissing() {
        return propertyService.findByChar('M');
    }

    @GetMapping("/removed")
    public  List<Property>findRemoved() {
       return propertyService.findOutDate();
   }

    @GetMapping("/add")
    public String add () throws FileNotFoundException { return  propertyService.addToDB();
    }

    @GetMapping(value="/id/{id}")
    public Property findById(@PathVariable  int id){return propertyService.findById(id);}

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping (value = "/delete/{id}")
    public void deleteProperty (@PathVariable  int id){
        propertyService.deleteProperty(id);
    }

    @PostMapping("/saveProperty")
    public void create(@RequestBody Map<String, String> body){
        propertyService.add(body.get("propertyId"), body.get("propertyName"),body.get("propertyRoom"),body.get("propertyPrice"),body.get("propertyInDate"),
                body.get("propertyOutDate"),body.get("charState"),body.get("intType"));
    }

}
