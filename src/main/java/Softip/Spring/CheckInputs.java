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
    public static ArrayList<String> checkInputs (String[] args, String fileName, String direcotry) throws IOException {
        ArrayList<String> used = new ArrayList<String>();
        //String path = System.getProperty("user.dir");
        String separator = File.separator;
        Path pathToFile = Paths.get(direcotry +separator+fileName);
        File file = new File(String.valueOf(pathToFile));
        if (!file.exists()){
            logger.error("Zadany subor neexistuje: "+fileName);
        }
        else {
            try {
                BufferedReader br = new BufferedReader(Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8));
                //nacitanie prveho riadku s udajmi hlavicky
                String line = br.readLine();
                while (line != null) {
                    used.add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        System.out.println("USED: "+used);

        String[] input = String.join( " ", args ).split("[, ]");
        ArrayList<String> inputs = new ArrayList<String>();
        FileWriter fileWriter = new FileWriter(String.valueOf(pathToFile),true);
        int i;
        for (i=0; i<input.length;i++){
            input[i]= input[i]+".csv";
            if (!inputs.contains(input[i]) && !used.contains(input[i])){
                inputs.add(input[i]);
                fileWriter.write(input[i]+'\n');
            }
            else {
                logger.warn("Pokus o opakovany import: "+input[i]);
                System.out.println(input[i] + " bol zadany viackrat (nacital sa len raz)");
            }


        }
        fileWriter.close();

        return inputs;
    }


}
