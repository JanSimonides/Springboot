package Softip.Spring;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "type")
public class Type  implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private  long id_type;
    @Column(name = "int_type", unique = true, nullable = false)
    private int int_type;
    @Column(name = "description")
    private String description;

    public Type(int int_type, String description) {
        this.int_type = int_type;
        this.description = description;
    }

    public Type(){

    }

    public long getId_type() {
        return id_type;
    }

    public void setId_type(long id_type) {
        this.id_type = id_type;
    }

    public int getInt_type() {
        return int_type;
    }

    public void setInt_type(int int_type) {
        this.int_type = int_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
