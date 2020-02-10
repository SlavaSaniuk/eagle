package by.bsac.domain.dto;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoProperty;
import by.bsac.domain.ImageExtension;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Dto({UserImagesContext.class, ImageFile.class, Image.class})
@Getter @Setter
@NoArgsConstructor
public class ContextWithImageDto {

    @JsonProperty("context_id")
    @DtoProperty(entityProperty = "context_id")
    private Integer context_id; //UserImagesContext

    @JsonProperty("image_id")
    @DtoProperty(entityProperty = "image_id")
    private Long image_id; //ImageFile

    @JsonProperty("image_name")
    @DtoProperty(entityProperty = "image_name")
    private String image_name; //Image

    @JsonProperty("image_path")
    private String image_path; //ImageFile

    @JsonProperty("image_data")
    private byte[] image_data; //Image

    @JsonProperty("image_extension")
    private ImageExtension image_extension; //Image

}
