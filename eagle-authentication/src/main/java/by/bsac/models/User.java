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
    private Integer user_id;

    @Column(name = "user_id_alias", unique = true, length = 30)
    private String user_id_alias;

    @OneToOne(mappedBy = "account_user", fetch = FetchType.LAZY)
    private Account user_account;

}
