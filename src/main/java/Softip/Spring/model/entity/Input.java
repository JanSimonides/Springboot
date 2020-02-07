package Softip.Spring.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "input")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Input implements Serializable {
    private static  final long serialVersionUID = 1L;


    @Id @GeneratedValue
    private int fileId;
    @Column(name = "file_name", unique = true, nullable = false)
    private  String fileName;

    public Input (String fileName){
        this.fileName = fileName;
    }
}
