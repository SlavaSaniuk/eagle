package by.bsac.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "user_id_alias", unique = true)
    private String user_id_alias;

    @OneToOne(mappedBy = "details_user", fetch = FetchType.LAZY)
    private UserDetails user_details;

}
