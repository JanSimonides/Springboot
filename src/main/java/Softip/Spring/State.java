package Softip.Spring;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "state")
public class State  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long id_state;
    @Column(name = "char_state", unique = true, nullable = false)
    private Character char_state;
    @Column(name = "description")
    private String description;
    @OneToMany(    fetch = FetchType.EAGER, mappedBy = "propertyState", cascade = CascadeType.ALL)
    private Set<Property> properties;

    public State() {

    }

    public State(Character ID_state, String description) {
        this.char_state = ID_state;
        this.description = description;
    }

    public State(Character c, Property... properties) {
        this.char_state = c;
        this.properties = Stream.of(properties).collect(Collectors.toSet());
        this.properties.forEach(x -> x.setPropertyState(this));
    }

    public char getChar_state() {
        return char_state;
    }

    public void setChar_state(char ID_state) {
        this.char_state = ID_state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId_state() {
        return id_state;
    }

    public void setId_state(long id_state) {
        this.id_state = id_state;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
}
