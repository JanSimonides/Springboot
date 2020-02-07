package Softip.Spring.controller;

import Softip.Spring.YamlCfg;
import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.mapper.StateMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.PropertyRepository;
import Softip.Spring.repository.StateRepository;
import Softip.Spring.repository.TypeRepository;
import Softip.Spring.service.PropertyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;

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
    public List<PropertyDTO> findall() {
        return propertyService.findAll() ;
    }

    @GetMapping("/ok")
    public  List<PropertyDTO> findOk() {
        return propertyService.findByChar('O');
    }

    @GetMapping("/moved")
    public  List<PropertyDTO> findMoved() {
        return propertyService.findByChar('V');
    }

    @GetMapping("/missing")
    public  List<PropertyDTO> findMissing() {
        return propertyService.findByChar('M');
    }

   @GetMapping("/removed")
    public @ResponseBody List<PropertyDTO>findRemoved() {
       return propertyService.findOutDate();
   }

    @GetMapping("/add")
    public String add () throws FileNotFoundException { return  propertyService.addToDB();
    }

}
