package by.bsac.repositories;

import by.bsac.domain.models.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ImagesFilesRepository")
public interface ImagesFilesJpaRepository extends JpaRepository<ImageFile, Long> {

}
