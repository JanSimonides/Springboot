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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Type  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long idType;
    @Column(name = "int_type", unique = true, nullable = false)
    private int intType;
    @Column(name = "description")
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "propertyType", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Property> typeProperties;

    public Type(int int_type, String description) {
        this.intType = int_type;
        this.description = description;
    }

    /*public Type(int i, Property... properties) {
        this.intType = i;
        this.typeProperties = Stream.of(properties).collect(Collectors.toSet());
        this.typeProperties.forEach(x -> x.setPropertyType(this));
    }*/

}
