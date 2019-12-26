package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Account status entity represent a state od account. Account can be:
 * 'CREATED' - when new account is register, account status automatically set to {@link Status#CREATED};
 * 'CONFIRMED' - when account user fills user details information.
 */
@Entity
@Table(name = "account_status")
@Getter @Setter
public class AccountStatus implements Serializable {

    @Id
    private Integer status_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private Status status;

    @OneToOne
    @JoinColumn(name = "status_id")
    @MapsId
    private Account account;

    /**
     * Construct new AccountStatus
     * object with status {@link Status#CREATED}
     */
    public AccountStatus() {
        this.status = Status.CREATED;
    }

}
