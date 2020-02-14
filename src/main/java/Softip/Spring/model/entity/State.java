package Softip.Spring.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "state")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class State  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long idState;
    @Column(name = "char_state", unique = true, nullable = false)
    private Character charState;
    @Column(name = "description")
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyState", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Property> stateProperties;

    public State(Character ID_state, String description) {
        this.charState = ID_state;
        this.description = description;
    }

   /* public State(Character c, Property... properties) {
        this.charState = c;
        this.properties = Stream.of(properties).collect(Collectors.toSet());
        this.properties.forEach(x -> x.setPropertyState(this));
    }*/

}
