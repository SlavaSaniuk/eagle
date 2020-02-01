package by.bsac.repositories;

import by.bsac.domain.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserCrudRepository")
public interface UserCrudRepository extends CrudRepository<User, Integer> {

}
