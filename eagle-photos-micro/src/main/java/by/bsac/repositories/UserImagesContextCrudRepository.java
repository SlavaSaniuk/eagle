package by.bsac.repositories;

import by.bsac.domain.models.UserImagesContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserImagesContextRepository")
public interface UserImagesContextCrudRepository extends CrudRepository<UserImagesContext, Integer> {

}
