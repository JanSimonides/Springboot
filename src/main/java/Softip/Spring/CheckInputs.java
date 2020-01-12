package Softip.Spring;


import org.apache.log4j.Logger;
import java.util.ArrayList;

public class CheckInputs {

    private final static Logger logger = Logger.getLogger(Application.class);

    public static ArrayList<String> checkInputs (String[] args){
        String[] input = String.join( " ", args ).split("[, ]");
        ArrayList<String> inputs = new ArrayList<String>();

        int i;
        for (i=0; i<input.length;i++){
            input[i]= input[i]+".csv";
            if (!inputs.contains(input[i])){
                inputs.add(input[i]);
            }
            else {
                logger.warn("Pokus o opakovany import: "+input[i]);
                System.out.println(input[i] + " bol zadany viackrat (nacital sa len raz)");
            }
        }
        return inputs;
    }


}
