package Softip.Spring;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "state")
public class State  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id
    @Column(name = "char_state", unique = true, nullable = false)
    private Character ID_state;


    public State() {

    }

    public State(char x) {
        this.ID_state = x;
    }

    public char getChar_state() {
        return ID_state;
    }

    public void setChar_state(char ID_state) {
        this.ID_state = ID_state;
    }
}
