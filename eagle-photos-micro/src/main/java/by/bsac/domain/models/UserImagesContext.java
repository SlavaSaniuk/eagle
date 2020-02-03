package by.bsac.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_images_context")
@Getter @Setter
public class UserImagesContext {

    @Id
    private Integer context_id;

    @OneToOne(optional = false)
    @JoinColumn(name = "context_id")
    @MapsId
    private User images_owner;

    @OneToMany(mappedBy = "images_context")
    private Set<ImageFile> user_images = new HashSet<>();

    @Override
    public String toString() {
        return "UserImagesContext{" +
                "context_id=" + context_id +
                '}';
    }

    public boolean addImageFile(ImageFile a_file) {
        return this.user_images.add(a_file);
    }
}
