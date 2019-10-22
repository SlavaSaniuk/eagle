package by.bsac.repositories;

import by.bsac.models.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepository extends CrudRepository<UserDetails, Integer> {
}
