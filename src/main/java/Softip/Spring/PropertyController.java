package Softip.Spring;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;



@RestController
public class PropertyController {

    private final static Logger logger = Logger.getLogger(PropertyController.class);

    @Autowired
    private
    PropertyRepositery propertyRepositery;

    String home = "<a href=\"/\">\n" +
            "      <H1>Home</H1>\n" +
            "    </a>";

    /*@GetMapping("/findall")
    public String findAll(){
        String result = "<html>";
        for (Property prop : propertyRepositery.findAll()){
            result +="<div>"+prop.toString()+"</div>";
        }
        result += home;
        return result+"</html>";

    }*/


    @GetMapping(path="/all")
    public @ResponseBody  List<Property> getAllTrack() {
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
    public String add (){
        String result = "<html>";
        ArrayList<Property> properties = null;
        try {
            properties =  ReadCsv.readProperty("inventuraVzor1.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Property p : properties){
            try{
             propertyRepositery.save(p);
            } catch (Exception e) {
                System.out.println("Do databazy neboli pridane: "+p);
                logger.warn("Do databazy nebol pridany objekt: "+ p.toString());
            }
        }
            result += " Pridane do databazy ";
        result += home;
        return result+"</html>";
    }

}
