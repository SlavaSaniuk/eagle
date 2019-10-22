package by.bsac.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Setter
    @Id
    @Column(name = "user_id")
    private Integer user_id;

    @Setter
    @Column(name = "user_id_alias")
    private String user_id_alias;

}
