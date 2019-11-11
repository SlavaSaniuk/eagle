package by.bsac.repositories;

import by.bsac.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.user_id_alias = ?1")
    @Nullable User findByUserIdAlias(String id_alias);

}