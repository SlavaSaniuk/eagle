package by.bsac.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Getter
public class Account implements Serializable {

    @Id
    @Column(name = "account_id")
    @Setter
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
    private User account_user;

    @Transient
    @Setter
    private transient String account_password;

    @JsonIgnore
    public void setAccountUser(User account_user) {
        this.account_user = account_user;
    }
}
