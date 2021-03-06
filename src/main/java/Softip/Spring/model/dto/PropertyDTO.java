package Softip.Spring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {

    private int propertyId;
    private  String propertyName;
    private  String propertyRoom;
    @JsonIgnore
    private int intForType;
    private TypeDTO propertyTypeDTO;
    private float propertyPrice;
    private LocalDate propertyInDate;
    private LocalDate propertyOutDate;
    private StateDTO propertyStateDTO;
    @JsonIgnore
    private Character character;

    public PropertyDTO(String[] parameters)  {
        if  (parameters[6].length() > 1) {
            this.propertyOutDate = LocalDate.parse(parameters[6].substring(0, 4) + "-" + parameters[6].substring(4, 6) + "-" + parameters[6].substring(6, 8));
        }
        else {
            this.propertyOutDate = null;
        }

        this.propertyId = Integer.parseInt(parameters[0]);
        this.propertyName = parameters[1];
        this.propertyRoom = parameters[2];
        this.intForType = Integer.parseInt(parameters[3]);
        this.propertyPrice = Float.parseFloat(parameters[4].substring(0, parameters[4].length() - 3).replace(",","."));
        this.propertyInDate =LocalDate.parse(parameters[5].substring(0,4)+"-" + parameters[5].substring(4,6)+"-"+ parameters[5].substring(6,8));
        this.character = parameters[7].charAt(0);
    }
}
