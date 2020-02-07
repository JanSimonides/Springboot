package Softip.Spring.model.dto;

import Softip.Spring.model.entity.Property;
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
public class StateDTO {
    private  long idState;
    private Character charState;
    private String description;

    @JsonIgnore
    private Set<PropertyDTO> properties;
}
