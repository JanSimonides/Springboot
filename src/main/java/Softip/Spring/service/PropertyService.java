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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        File sourceFolder = new File(yamlCfg.getDirectoryNew());
        String pathToDest = yamlCfg.getDirectoryUsed();
        String pathToErrors = yamlCfg.getDirectoryError();
        File[] inputs = sourceFolder.listFiles();

        Property propertyToSave;
        String result = "<html>";
        Property propertyLog;
        ArrayList<PropertyDTO> properties = null;
        Boolean wrongData = false;
        int i,err=0;
        if (inputs.length > 0) {
            for (i = 0; i < inputs.length; i++) {
                if (!inputRepository.existsByFileName(inputs[i].getName())) {
                    Input input = new Input(inputs[i].getName());
                    inputRepository.save(input);
                    properties = ReadCsv.readProperty(inputs[i]);
                    for (PropertyDTO p : properties) {
                        try {
                            propertyToSave = propertyMapper.toEntity(p);
                            //nastavenie statu
                            propertyToSave.setPropertyState(stateService.findState(p.getCharacter()));
                            //nastavenie typu
                            propertyToSave.setPropertyType(typeService.findType(p.getIntForType()));
                            propertyRepository.save(propertyToSave);
                        } catch (Exception e) {
                            wrongData = true;
                            System.out.println("Do databazy neboli pridane: " + propertyMapper.toEntity(p).toString());
                            logger.warn("Do databazy nebol pridany objekt: " + propertyMapper.toEntity(p).getPropertyName() + " zo subora: " + inputs[i].getName());
                        }
                    }
                    if (wrongData){
                        File duplicateFolder = new File(pathToErrors + inputs[i].getName());
                        logger.warn("Inventura " + inputs[i].getName()+ " obsahuje chybne udaje");
                        err++;
                        try {
                            Files.move(inputs[i].toPath(),duplicateFolder.toPath(),StandardCopyOption.REPLACE_EXISTING);
                            inputs[i].delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            File destinationFolder = new File(pathToDest + inputs[i].getName());
                            Files.move(inputs[i].toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            logger.warn("Inventura " + inputs[i].getName()+ " zaevidovana uspesne");
                            inputs[i].delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    File errorsFolder = new File(pathToErrors + inputs[i].getName());
                    logger.warn("Inventura " + inputs[i].getName()+ "sa uz nachadza v databaze");
                    err++;
                    try {
                        Files.move(inputs[i].toPath(),errorsFolder.toPath(),StandardCopyOption.REPLACE_EXISTING);
                        inputs[i].delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                wrongData = false;
            }
        }
        else{
            logger.info("Priecinok je prazdny");
        }
        logger.info("Koniec kontroly");
        logger.info("Evidovanie "+ inputs.length+" inventur do databazy. "+(inputs.length-err)+"  uspesne a "+err+"  neuspesne\n");
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
