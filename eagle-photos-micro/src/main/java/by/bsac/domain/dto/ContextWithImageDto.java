package by.bsac.domain.dto;

import by.bsac.annotations.Dto;
import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Dto({UserImagesContext.class, ImageFile.class, Image.class})
@Getter @Setter
@NoArgsConstructor
public class ContextWithImageDto {

    private Integer context_id; //UserImagesContext

    private Long image_id; //ImageFile

    private String image_name; //Image

    private String image_path; //ImageFile

    private byte[] image_data; //Image

    private Extension image_extension; //Image

    public enum Extension {
        JPG,
        PNG
    }
}
