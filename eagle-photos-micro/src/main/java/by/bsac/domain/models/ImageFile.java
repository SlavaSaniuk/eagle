package by.bsac.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageFile {

    private Long image_id;

    private User image_owner;

    private String image_path;

}
