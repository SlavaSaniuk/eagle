package by.bsac.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image_file")
@Getter @Setter
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long image_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "images_context")
    private UserImagesContext images_context;

    @Column(name = "image_path")
    private String image_path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageFile imageFile = (ImageFile) o;
        return image_id.equals(imageFile.image_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image_id);
    }

}
