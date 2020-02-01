package by.bsac.repositories;

import by.bsac.domain.models.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("ImagesFilesRepository")
public interface ImagesFilesJpaRepository extends JpaRepository<ImageFile, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ImageFile if SET if.image_path = :image_path WHERE if.image_id = :image_id")
    @Transactional
    void updateImagePath(Long image_id, String image_path);

    @Query("SELECT if FROM ImageFile if WHERE if.image_path = :image_path")
    ImageFile findByImagePath(String image_path);
}
