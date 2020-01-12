package Softip.Spring;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;

@SpringBootApplication
public class Application {
	private final static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {

		String path = System.getProperty("user.dir");
		String separator = File.separator;
		PropertyConfigurator.configure(path+separator+"classes"+separator+"log4j.properties");

		String[] input = String.join( " ", args ).split("[, ]");
		if (input.length > 10) {
			logger.error("Chyba zadal si prilis vela parametrov: "+input.length);
			System.out.println("Chyba zadal si prilis vela parametrov: "+input.length);
			System.exit(0);
		}
		ArrayList<String> inputs = new ArrayList<String>();
		//pridanie pripny csv a kontrola duplikatov
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

		SpringApplication.run(Application.class, args);

	}
}
