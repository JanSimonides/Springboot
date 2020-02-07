package Softip.Spring;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.util.ArrayList;

@EnableAutoConfiguration
@EntityScan("Softip.Spring")
@SpringBootApplication
public class Application {
	private final static Logger logger = Logger.getLogger(Application.class);


	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		String separator = File.separator;
		//PropertyConfigurator.configure(path+separator+"src"+separator+"main"+separator+"resources"+separator+"log4j.properties");
		PropertyConfigurator.configure(path+separator+"classes"+separator+"log4j.properties");

		String[] input = String.join( " ", args ).split("[, ]");
		if (input.length > 10) {
			logger.error("Chyba zadal si prilis vela parametrov: "+input.length);
			System.out.println("Chyba zadal si prilis vela parametrov: "+input.length);
			System.exit(0);
		}

		SpringApplication.run(Application.class, args);

	}

}
