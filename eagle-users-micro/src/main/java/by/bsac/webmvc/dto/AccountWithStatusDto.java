package by.bsac.webmvc.dto;

import by.bsac.annotations.Dto;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Dto({Account.class, AccountStatus.class})
@Getter @Setter
@NoArgsConstructor
@ToString
public class AccountWithStatusDto {

    private Integer account_id;

    private String account_email;

    private AccountStatus.AccountStatuses account_status;


}
