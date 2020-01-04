package by.bsac.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AccountStatus {

    public enum AccountStatuses {
        CREATED,
        CONFIRMED
    }


    private Account account;

    private AccountStatuses account_status;

}
