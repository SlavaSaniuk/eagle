package by.bsac.services.images;

import by.bsac.domain.models.ImageFile;
import by.bsac.services.CrudService;

public interface ImagesFilesCrudService extends CrudService<ImageFile, Long> {

    void updateImagePath(ImageFile entity, String image_path);
}
