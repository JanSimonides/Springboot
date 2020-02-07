package Softip.Spring;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CheckInputs {

    private final static Logger logger = Logger.getLogger(Application.class);
    public static ArrayList<String> checkInputs (String[] args,  String direcotry) throws IOException {
        String separator = File.separator;
        Path pathToFile = Paths.get(direcotry +separator);

        String[] input = String.join( " ", args ).split("[, ]");
        ArrayList<String> inputs = new ArrayList<String>();

        int i;
        for (i=0; i<input.length;i++) {
            input[i] = input[i] + ".csv";
            //kontroluje ci sa subor nachadza v zadanom priecinku
            File file = new File(String.valueOf(pathToFile+separator+input[i]));
            System.out.println(file.exists());
            if (!file.exists()){
                logger.error("Zadany subor neexistuje: "+input[i]);
            }
            else {
                inputs.add(input[i]);
            }
        }

        return inputs;
    }


}
