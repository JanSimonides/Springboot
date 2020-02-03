package Softip.Spring.model.dto;

import Softip.Spring.model.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDTO {

    private int propertyId;
    private  String propertyName;
    private  String propertyRoom;
    private int propertyType;
    private float propertyPrice;
    private LocalDate propertyInDate;
    private LocalDate propertyOutDate;
    private StateDTO propertyStateDTO;
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
        this.propertyType = Integer.parseInt(parameters[3]);
        this.propertyPrice = Float.parseFloat(parameters[4].substring(0, parameters[4].length() - 3).replace(",","."));
        this.propertyInDate =LocalDate.parse(parameters[5].substring(0,4)+"-" + parameters[5].substring(4,6)+"-"+ parameters[5].substring(6,8));
        this.character = parameters[7].charAt(0);
    }
}
