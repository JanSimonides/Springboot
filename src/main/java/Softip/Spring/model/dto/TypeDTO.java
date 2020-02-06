package Softip.Spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDTO {

    private  long idType;
    private int intType;
    private String description;
    private Set<PropertyDTO> properties;


}
