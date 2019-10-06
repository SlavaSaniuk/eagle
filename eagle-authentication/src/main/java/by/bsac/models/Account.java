package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
public class Account {

    @Id
    @Column(name = "account_id")
    private Integer account_id;

    @Column(name = "account_email", nullable = false, unique = true)
    @Setter
    private String account_email;

    @Column(name = "account_password_hash", nullable = false, length = 128)
    @Setter
    private String account_password_hash;

    @Column(name = "account_password_salt", nullable = false, length = 128)
    @Setter
    private String account_password_salt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    @MapsId
    @Setter
    private User account_user;
}
