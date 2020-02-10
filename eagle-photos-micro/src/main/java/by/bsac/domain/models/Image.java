package by.bsac.domain.models;

import by.bsac.domain.ImageExtension;
import by.bsac.domain.dto.ContextWithImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

@NoArgsConstructor
@Getter @Setter
public class Image {

    private String image_name;

    private ImageExtension image_extension;

    private byte[] image_data;

    private Path image_path;

    private ImageFile image_file;

    /**
     * Return image name with extension. For example: "image.jpg";
     * @return - Return image name with extension.
     */
    public String getFullName() {
        return String.format("%s.%s", this.image_name, this.image_extension.toString().toLowerCase());
    }

    @Override
    public String toString() {
        return "Image{" +
                "image_name='" + image_name + '\'' +
                ", image_extension=" + image_extension +
                '}';
    }
}
