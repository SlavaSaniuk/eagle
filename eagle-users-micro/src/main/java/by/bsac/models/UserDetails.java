package by.bsac.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_detail")
@Getter @Setter
public class UserDetails {

    @Id
    private Integer detail_id;

    @Embedded
    private UserName user_name;

    @OneToOne(optional = false)
    @JoinColumn(name = "detail_id")
    @MapsId
    @JsonManagedReference
    private User details_user;


}
