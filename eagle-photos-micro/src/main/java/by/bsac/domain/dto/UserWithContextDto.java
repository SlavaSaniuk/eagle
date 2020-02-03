package by.bsac.domain.dto;

import by.bsac.annotations.Dto;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Dto({User.class, UserImagesContext.class})
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserWithContextDto {

    private Integer user_id;

    private Integer context_id;

}
