package Softip.Spring.model.dto;

import Softip.Spring.model.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StateDTO {
    private  long idState;
    private Character charState;
    private String description;
    private Set<PropertyDTO> properties;
}
