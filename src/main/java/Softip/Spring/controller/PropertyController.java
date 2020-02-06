package Softip.Spring.controller;

import Softip.Spring.CheckInputs;
import Softip.Spring.ReadCsv;
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
import Softip.Spring.service.StateService;
import Softip.Spring.service.TypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PropertyController {

    private final static Logger logger = Logger.getLogger(PropertyController.class);

    @Autowired
    private
    PropertyService propertyService;

    @Autowired
    private
    StateService stateService;

    @Autowired
    private
    PropertyRepository propertyRepository;

    @Autowired
    private
    TypeRepository typeRepository;

    @Autowired
    private
    StateRepository stateRepository;

    @Autowired
    ApplicationArguments appArgs;

    @Autowired
    PropertyMapper propertyMapper;

    @Autowired
    StateMapper stateMapper;

    @Autowired
    TypeService typeService;

    @Autowired
    private YamlCfg yamlCfg;

    String home = "<a href=\"/\">\n" +
            "      <H1>Home</H1>\n" +
            "    </a>";
    @PostConstruct
    public  void init (){
        Type type0 = new Type(0,"IMA");
        Type type1 = new Type(1,"DIM");
        State state3= new State('V',"Moved");
        State state2 = new State('M',"Missing");
        State state1 = new State('O',"Ok");

        try {
            stateRepository.save(state1);
            stateRepository.save(state2);
            stateRepository.save(state3);
            typeRepository.save(type0);
            typeRepository.save(type1);
        }catch (Exception e){

        }


    }

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
    public List<Property>  findOk() {
        return propertyService.findAllProperties();
    }

  /*  @GetMapping("/moved")
    public @ResponseBody List<Property> findMoved(){

        //return propertyRepositery.findByPropertyStateCharStateOrderByPropertyInDateAsc('V');
    }*/

    /*@GetMapping("/missing")
    public@ResponseBody List<Property> findMissing(){
        return propertyRepositery.findByPropertyStateOrderByPropertyPriceAsc('M');
    }*/
    /*
    @GetMapping("/removed")
    public @ResponseBody List<Property>findRemoved(){
            return propertyRepositery.findByPropertyOutDateNotNull();
        }
*/
    @GetMapping("/add")
    public String add () throws FileNotFoundException {

        ArrayList<String> inputs = null;
        try {
            inputs = CheckInputs.checkInputs(appArgs.getSourceArgs(),"used.txt", yamlCfg.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Property propertyToSave;
        System.out.println("ARG: "+inputs);
        String result = "<html>";
        ArrayList<PropertyDTO> properties = null;
        int i;
        for (i = 0; i < inputs.size(); i++) {
            properties = ReadCsv.readProperty(inputs.get(i),yamlCfg.getDirectory());
            for (PropertyDTO p : properties) {
                try {
                    propertyToSave = propertyMapper.toEntity(p);
                    //nastavenie statu
                    propertyToSave.setPropertyState(stateService.findState(p.getCharacter()));
                    //nastavenie typu
                    propertyToSave.setPropertyType(typeService.findType(p.getIntForType()));
                    propertyRepository.save(propertyToSave);
                } catch (Exception e) {
                    System.out.println("Do databazy neboli pridane: " + propertyMapper.toEntity(p));
                    logger.warn("Do databazy nebol pridany objekt: " + propertyMapper.toEntity(p).toString() + " zo subora: " + inputs.get(i));
                }
            }
        }
        result += " Pridane do databazy ";
        result += home;
        return result + "</html>";
    }

}
