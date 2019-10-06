package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Getter @Setter
public class Account implements Serializable {

    @Id
    @Column(name = "account_id")
    private Integer account_id;

    @Column(name = "account_email", nullable = false, unique = true)
    private String account_email;

    @Column(name = "account_password_hash", nullable = false, length = 128)
    private String account_password_hash;

    @Column(name = "account_password_salt", nullable = false, length = 128)
    private String account_password_salt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    @MapsId
    private User account_user;

    @Transient
    private transient String account_password;

}
