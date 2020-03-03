package Softip.Spring.service;


import Softip.Spring.CheckInputs;
import Softip.Spring.ReadCsv;
import Softip.Spring.YamlCfg;
import Softip.Spring.controller.PropertyController;
import Softip.Spring.mapper.PropertyMapper;
import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Input;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.InputRepository;
import Softip.Spring.repository.PropertyRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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


    public List<Property> findProperty(){
        return propertyRepository.findAll();
    }

    public List<Property> findByChar(char x){
        return propertyRepository.findByPropertyStateCharState(x);
}

    public List<Property> findOutDate(){
        return propertyRepository.findByPropertyOutDateNotNull();
    }

    public Property findById(int x){
        return  propertyRepository.findByPropertyId(x);

    }

    public void deleteProperty (int id){
        propertyRepository.delete(propertyRepository.findByPropertyId(id));
    }

    public String addToDB () throws FileNotFoundException {
        logger.info("Vkladanie udajov do databazy\n");
        ArrayList<String> inputs = null;
        try {
            inputs = CheckInputs.checkInputs(appArgs.getSourceArgs(), yamlCfg.getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> toRemove = new ArrayList<String>();

        if(Objects.requireNonNull(inputs).size() > 0)
            for (String s : inputs) {
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
                        logger.warn("Do databazy nebol pridany objekt: " + propertyMapper.toEntity(p).getPropertyName() + " zo subora: " + inputs.get(i));
                    }
                }
            }
        result += " Pridane do databazy ";
        result += home;
        return result + "</html>";
    }

    public void add(String propertyId, String propertyName, String propertyRoom, String propertyPrice, String propertyInDate, String propertyOutDate, String propertyState, String propertyType) {
        int pId = Integer.parseInt(propertyId);
        float pPrice = Float.parseFloat(propertyPrice);
        LocalDate pInDate =  LocalDate.parse(propertyInDate.substring(0, 4) + "-" + propertyInDate.substring(4, 6) + "-" + propertyInDate.substring(6, 8));
        LocalDate pOutDate = null;

        if (!propertyOutDate.isEmpty()) {
            pOutDate = LocalDate.parse(propertyOutDate.substring(0, 4) + "-" + propertyOutDate.substring(4, 6) + "-" + propertyOutDate.substring(6, 8));
        }
        Property property = new Property(pId,propertyName,propertyRoom,pPrice,pInDate,pOutDate,stateService.findState(propertyState.charAt(0)),typeService.findType(Integer.parseInt(propertyType)));

        propertyRepository.save(property);
    }
}
