package by.bsac.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AccountStatus {

    public enum AccountStatuses {
        CREATED,
        CONFIRMED
    }

    @Getter @Setter
    private AccountStatuses account_status;

}
