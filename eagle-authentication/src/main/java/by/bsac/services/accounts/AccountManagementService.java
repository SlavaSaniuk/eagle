package by.bsac.services.accounts;

import by.bsac.models.Account;
import by.bsac.models.User;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface AccountManagementService {

    @Transactional
    User register(Account account);

    @Nullable User login(Account account);


}
