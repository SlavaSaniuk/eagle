package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import lombok.Getter;
import lombok.Setter;

@Dto({User.class, UserDetails.class})
@Getter @Setter
public class UserWithDetailsDto {

    private Integer user_id;

    private String user_id_alias;

    private UserName user_name;


}
