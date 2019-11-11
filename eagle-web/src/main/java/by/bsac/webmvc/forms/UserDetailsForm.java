package by.bsac.webmvc.forms;

import by.bsac.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDetailsForm {

    private Integer detailId;

    private String userFname;

    private String userLname;

    private User detailsUser;
}
