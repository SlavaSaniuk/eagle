package by.bsac.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_detail")
@Getter @Setter
public class UserDetails {

    @Id
    private Integer detail_id;

    @OneToOne
    @JoinColumn(name = "detail_id", nullable = false)
    @MapsId
    private User details_user;
}
