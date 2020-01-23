package Softip.Spring;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "state")
public class State  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long id_state;
    @Column(name = "char_state", unique = true, nullable = false)
    private Character ID_state;
    @Column(name = "description")
    private String description;


    public State() {

    }

    public State(Character ID_state, String description) {
        this.ID_state = ID_state;
        this.description = description;
    }

    public char getChar_state() {
        return ID_state;
    }

    public void setChar_state(char ID_state) {
        this.ID_state = ID_state;
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
}
