package by.bsac.domain.dto;

import by.bsac.annotations.Dto;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Dto({User.class, UserImagesContext.class})
@Getter @Setter
@NoArgsConstructor
@ToString
public class UserWithContextDto {

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("context_id")
    private Integer context_id;

}
