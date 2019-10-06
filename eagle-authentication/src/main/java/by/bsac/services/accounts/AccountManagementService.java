package by.bsac.services.accounts;

import by.bsac.models.Account;
import by.bsac.models.User;

public interface AccountManagementService {

    User register(Account account);

    User login(Account account);


}
