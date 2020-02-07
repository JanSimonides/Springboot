package Softip.Spring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Set<PropertyDTO> properties;


}
