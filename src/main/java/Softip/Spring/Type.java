package Softip.Spring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "type")
public class Type  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_type", unique = true, nullable = false)
    private int ID_types;

    public Type(int i) {
        this.ID_types = i;
    }

    public Type() {
    }

    public int getID_types() {
        return ID_types;
    }

    public void setID_types(int ID_types) {
        this.ID_types = ID_types;
    }
}
