package Softip.Spring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PropertyController {

    private final static Logger logger = Logger.getLogger(PropertyController.class);

    @Autowired
    private
    PropertyRepositery propertyRepositery;

    @Autowired
    private
    TypeRepository typeRepository;

    @Autowired
    private
    StateRepositery stateRepositery;

    @Autowired
    ApplicationArguments appArgs;

    @Autowired
    private YamlCfg yamlCfg;

    String home = "<a href=\"/\">\n" +
            "      <H1>Home</H1>\n" +
            "    </a>";
    @PostConstruct
    public  void init (){
        Type type0 = new Type(0,"Popis");
        Type type1 = new Type(1,"Popis");
        State state3= new State('V',"Moved");
        State state2 = new State('M',"Missing");
        State state1 = new State('O',"Ok");

        try {
            stateRepositery.save(state1);
            stateRepositery.save(state2);
            stateRepositery.save(state3);
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
    public @ResponseBody  List<Property> findall() {
        return propertyRepositery.findAll();
    }

    @GetMapping("/ok")
    public @ResponseBody List<Property>  findOk() {
        return propertyRepositery.findByPropertyStateOrderByPropertyPriceDesc('O');
    }

    @GetMapping("/moved")
    public @ResponseBody List<Property> findMoved(){

        return propertyRepositery.findByPropertyStateOrderByPropertyInDateAsc('V');
    }

    @GetMapping("/missing")
    public@ResponseBody List<Property> findMissing(){
        return propertyRepositery.findByPropertyStateOrderByPropertyPriceAsc('M');
    }

    @GetMapping("/removed")
    public @ResponseBody List<Property>findRemoved(){
            return propertyRepositery.findByPropertyOutDateNotNull();
        }

    @GetMapping("/add")
    public String add () throws FileNotFoundException {

        ArrayList<String> inputs = null;
        try {
            inputs = CheckInputs.checkInputs(appArgs.getSourceArgs(),"used.txt", yamlCfg.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ARG: "+inputs);
        String result = "<html>";
        ArrayList<Property> properties = null;

        int i;
        for (i = 0; i < inputs.size(); i++) {
            properties = ReadCsv.readProperty(inputs.get(i),yamlCfg.getDirectory());
            for (Property p : properties) {
                System.out.println(1);
                try {
                    propertyRepositery.save(p);
                } catch (Exception e) {
                    System.out.println("Do databazy neboli pridane: " + p);
                    logger.warn("Do databazy nebol pridany objekt: " + p.toString() + " zo subora: " + inputs.get(i));
                }
            }
        }
        result += " Pridane do databazy ";
        result += home;
        return result + "</html>";
    }


}
