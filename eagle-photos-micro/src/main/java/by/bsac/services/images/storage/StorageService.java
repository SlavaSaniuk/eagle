package by.bsac.services.images.storage;

import by.bsac.domain.models.Image;
import by.bsac.domain.models.ImageFile;
import by.bsac.domain.models.UserImagesContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component("StorageService")
public interface StorageService {

    /**
     *  Save new image to filesystem. Method get {@link UserImagesContext} entity from database by id.
     * Create new {@link ImageFile} entity, binds it to user images context and save it in database.
     * Next method save {@link Image#getImageData()} image in ... folder
     * with "image_{@link ImageFile#getImageId()}.{@link Image#getImageExtension()}" name.
     * @param a_context - {@link UserImagesContext} entity.
     * @param a_image_file - {@link ImageFile} entity.
     * @param a_image - {@link Image} model.
     * @return - Saved {@link ImageFile} entity.
     * @throws IOException - Throws if IO exception occurs.
     */
    @Transactional
    ImageFile saveImage(UserImagesContext a_context, ImageFile a_image_file, Image a_image) throws IOException;

    /**
     * Load image file entity from database and images storage. Method set {@link ImageFile#setImage(Image)}
     * transient property with {@link Image} value from images storage.
     * @param a_image_file - {@link ImageFile} entity with ID.
     * @return - {@link ImageFile} entity with {@link ImageFile#getImage()} property.
     * @throws IOException - if read {@link IOException} occurs.
     */
    ImageFile loadImage(ImageFile a_image_file) throws IOException;

    /**
     * Load image file entity from database and images storage. Method set {@link ImageFile#setImage(Image)}
     * transient property with {@link Image} value from images storage.
     * @param a_image_id - request ImageFile ID;
     * @return - {@link ImageFile} entity with {@link ImageFile#getImage()} property.
     * @throws IOException - if read {@link IOException} occurs.
     */
    ImageFile loadImage(Long a_image_id) throws IOException;

}
