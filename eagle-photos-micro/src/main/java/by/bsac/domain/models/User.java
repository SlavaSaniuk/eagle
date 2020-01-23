package by.bsac.domain.models;

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
    private Integer user_id;

    @Column(name = "user_id_alias")
    private String user_id_alias;

    @OneToOne(mappedBy = "images_owner", fetch = FetchType.EAGER)
    private UserImagesContext images_context;

}
