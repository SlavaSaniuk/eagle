package by.bsac.webmvc.dto;

import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithDetailsDto {

    //User properties
    private Integer user_id;
    private String user_id_alias;

    //Details properties
    private String user_fname;
    private String user_lname;

    public User getUserEntity() {
        User user = new User();

        user.setUserId(this.user_id);
        user.setUserIdAlias(this.user_id_alias);

        return user;
    }

    public UserDetails getDetailsEntity() {
        UserDetails details = new UserDetails();
        details.setUserName(new UserName(this.user_fname, this.user_lname));
        return details;
    }
}
