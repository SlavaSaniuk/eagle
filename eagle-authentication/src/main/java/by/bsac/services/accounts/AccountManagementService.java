package by.bsac.services.accounts;

import by.bsac.models.Account;
import by.bsac.models.User;
import org.springframework.lang.Nullable;

public interface AccountManagementService {

    User register(Account account);

    @Nullable User login(Account account);


}
