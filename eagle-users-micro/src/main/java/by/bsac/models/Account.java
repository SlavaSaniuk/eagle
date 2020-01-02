package by.bsac.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Account {

    private Integer account_id;

    private String account_email;

    private AccountStatus status;

}
