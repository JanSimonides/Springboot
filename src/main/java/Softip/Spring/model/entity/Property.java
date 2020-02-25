package Softip.Spring.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static lombok.AccessLevel.NONE;


@Entity
@Table(name = "property")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude ={ "propertyType","propertyState"})
@EqualsAndHashCode(exclude = { "propertyType","propertyState"})
public class Property implements Serializable {
    private static  final long serialVersionUID = 1L;

    @Id
    @Column(name = "property_id", unique = true, nullable = false)
    private int propertyId;
    @Column(name = "property_name", unique = true, nullable = false)
    private  String propertyName;
    @Column(name = "property_room")
    private  String propertyRoom;
    @Column(name = "property_price", nullable = false)
    private float propertyPrice;
    @Column(name = "property_on_date", nullable = false)
    private LocalDate propertyInDate;
    @Column(name = "property_out_date")
    private LocalDate propertyOutDate;
    @ManyToOne
    @JoinColumn(name="property_state", nullable = false)
    private State propertyState;
    @ManyToOne
    @JoinColumn(name="property_type", nullable = false)
    private Type propertyType;
}
