package by.bsac.repositories;

import by.bsac.models.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.account_email = :email")
    @Nullable Account foundByAccountEmail(@Param("email") String email);

}
