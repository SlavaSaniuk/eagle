package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.annotations.DtoProperty;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Dto({User.class, UserDetails.class})
@Getter @Setter
@ToString
public class UserWithDetailsDto {

    private Integer user_id;

    @DtoEmbedded(UserName.class)
    @DtoProperty(entityProperty = "first_name")
    private String user_fname;

    @DtoEmbedded(UserName.class)
    @DtoProperty(entityProperty = "last_name")
    private String user_lname;
}
