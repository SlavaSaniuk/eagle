package by.bsac.domain.dto;

import by.bsac.annotations.Dto;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Dto({UserImagesContext.class, ImageFile.class})
@Getter @Setter
@NoArgsConstructor
public class ContextWithImageDto {

    private Integer context_id;

    private Long image_id;

    private String image_path;

    

}
