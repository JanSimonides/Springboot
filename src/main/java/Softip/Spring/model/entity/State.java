package Softip.Spring.model.entity;

import Softip.Spring.model.entity.Property;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class State  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long idState;
    @Column(name = "char_state", unique = true, nullable = false)
    private Character charState;
    @Column(name = "description")
    private String description;
    @OneToMany(    fetch = FetchType.EAGER, mappedBy = "propertyState", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Property> properties;

    public State(Character ID_state, String description) {
        this.charState = ID_state;
        this.description = description;
    }

    public State(Character c, Property... properties) {
        this.charState = c;
        this.properties = Stream.of(properties).collect(Collectors.toSet());
        this.properties.forEach(x -> x.setPropertyState(this));
    }

}
