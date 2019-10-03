package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer user_id;

    @Column(name = "user_id_alias", unique = true, length = 30)
    public String user_id_alias;

}
