package Softip.Spring.service;


import Softip.Spring.CheckInputs;
import Softip.Spring.ReadCsv;
import Softip.Spring.YamlCfg;
import Softip.Spring.controller.PropertyController;
import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Input;
import Softip.Spring.model.entity.Property;
import Softip.Spring.repository.InputRepository;
import Softip.Spring.repository.PropertyRepository;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PropertyService {
    private final static Logger logger = Logger.getLogger(PropertyService.class);

    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    ApplicationArguments appArgs;
    @Autowired
    private YamlCfg yamlCfg;
    @Autowired
    InputRepository inputRepository;
    @Autowired
    private
    StateService stateService;
    @Autowired
    TypeService typeService;

    String home = "<a href=\"/\">\n" +
            "      <H1>Home</H1>\n" +
            "    </a>";


    public List<PropertyDTO> findAll(){
        List<Property> properties  = propertyRepository.findAll();

        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }

    public List<PropertyDTO> findByChar(char x){
        List<Property> properties  = propertyRepository.findByPropertyStateCharState(x);

        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }

    public List<PropertyDTO> findOutDate(){
        List<Property> properties  = propertyRepository.findByPropertyOutDateNotNull();

        return  properties.stream()
                .map(p -> propertyMapper.toDto(p))
                .collect(Collectors.toList());
    }


    public String addToDB () throws FileNotFoundException {
        logger.info("Vkladanie udajov do databazy\n");
        ArrayList<String> inputs = null;
        try {
            inputs = CheckInputs.checkInputs(appArgs.getSourceArgs(), yamlCfg.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int c=0;
        ArrayList<String> toRemove = new ArrayList<String>();

        if(Objects.requireNonNull(inputs).size() > 0)
            for (String s : inputs) {
                System.out.println(c++);
                Input input = new Input(s);
                try {
                    inputRepository.save(input);
                } catch (Exception e) {
                    logger.warn("Subor: " + s + " sa uz nachadza v databaze");
                    System.out.println("Subor: " + s + " sa uz nachadza v databaze");
                    toRemove.add(s);
                }
            }

        System.out.println("Inputs: " +inputs);
        System.out.println("ToRemove: "+ toRemove);

        for (String s : toRemove){
            inputs.remove(s);
        }

        Property propertyToSave;
        System.out.println("ARG: "+inputs);
        String result = "<html>";
        Property propertyLog;
        ArrayList<PropertyDTO> properties = null;
        int i;
        if (inputs.size() > 0)
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
                        System.out.println("Do databazy neboli pridane: " + propertyMapper.toEntity(p).toString());
                        logger.warn("Do databazy nebol pridany objekt: " + propertyMapper.toEntity(p).toString() + " zo subora: " + inputs.get(i));
                    }
                }
            }
        result += " Pridane do databazy ";
        result += home;
        return result + "</html>";
    }

}
