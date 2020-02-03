package Softip.Spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Softip.Spring.model.dto.PropertyDTO;
import Softip.Spring.model.entity.Property;
import org.apache.log4j.Logger;

public class ReadCsv {
    private final static Logger logger = Logger.getLogger(ReadCsv.class);
    public static ArrayList<PropertyDTO> readProperty(String fileName, String directory) throws FileNotFoundException {
        ArrayList<PropertyDTO> properties = new ArrayList<>();
        BufferedReader br= null;
        String line;
        String[] parameters;
            //System.out.println(fileName);
            //String path = System.getProperty("user.dir");
            String separator = File.separator;
            //path = path.substring(0,path.length()-6);

            Path pathToFile = Paths.get(directory +separator+fileName);
            //System.out.println(pathToFile);
            //kontrola vstupneho suboru
            File file = new File(String.valueOf(pathToFile));
            //System.out.println(!file.exists());
            if (!file.exists()){
                logger.error("Zadany subor neexistuje: "+fileName);
            }
            else {
                try {
                    br = new BufferedReader(Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8));
                    //nacitanie prveho riadku s udajmi hlavicky
                    String firstLine = br.readLine();
                    line = br.readLine();
                    while (line != null) {

                        parameters = line.split(";");
                        PropertyDTO propertyDTO = new PropertyDTO(parameters);

                        properties.add(propertyDTO);

                        line = br.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return properties;
    }
}
