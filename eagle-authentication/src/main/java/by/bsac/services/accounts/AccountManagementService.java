package by.bsac.services.accounts;

import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.models.User;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface AccountManagementService {

    @Transactional
    User register(Account account);

    @Nullable User login(Account account);

    /**
     * Confirm user account. Method change {@link AccountStatus#getStatus()}
     * status to {@link by.bsac.models.Status#CONFIRMED} value. Use this
     * method when user create user_details information.
     * @param account - {@link Account} to confirm.
     * @return - {@link Account} with account CONFIRMED status.
     */
    @Transactional
    Account confirmAccount(Account account);

}
