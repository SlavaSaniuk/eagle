package by.bsac.dto;

import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.models.Account;
import by.bsac.models.AccountStatus;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("TEST")
@SpringBootTest(classes = {DtoConvertersConfiguration.class})
public class AccountWithStatusDtoConverterIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountWithStatusDtoConverterIntegrationTest.class);

    //SpringBeans
    @Autowired @Qualifier("AccountWithStatusDtoConverter")
    private DtoEntityConverter<AccountWithStatusDto> converter;

    @Test
    void toDto_accountAndStatusEntities_shouldReturnDto() {

        final Integer ACCOUNT_ID = 24;
        final String ACCOUNT_EMAIL = "TEST@TEST.COM";
        final AccountStatus.AccountStatuses ACCOUNT_STATUS = AccountStatus.AccountStatuses.CONFIRMED;

        Account account = new Account();
        account.setAccountId(ACCOUNT_ID);
        account.setAccountEmail(ACCOUNT_EMAIL);

        AccountStatus account_status = new AccountStatus();
        account_status.setAccountStatus(ACCOUNT_STATUS);

        account.setStatus(account_status);
        account_status.setAccount(account);

        AccountWithStatusDto dto = this.converter.toDto(account, new AccountWithStatusDto());
        dto = this.converter.toDto(account_status, dto);

        Assertions.assertEquals(ACCOUNT_ID, dto.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, dto.getAccountEmail());
        Assertions.assertEquals(ACCOUNT_STATUS, dto.getAccountStatus());

        LOGGER.debug("AccountWithStatus DTO: " +dto.toString());
    }

    @Test
    void toEntity_accountWithStatusDto_shouldReturnAccountAndAccountStatusEntities() {

        final Integer ACCOUNT_ID = 24;
        final String ACCOUNT_EMAIL = "TEST@TEST.COM";
        final AccountStatus.AccountStatuses ACCOUNT_STATUS = AccountStatus.AccountStatuses.CREATED;

        AccountWithStatusDto dto = new AccountWithStatusDto();
        dto.setAccountId(ACCOUNT_ID);
        dto.setAccountEmail(ACCOUNT_EMAIL);
        dto.setAccountStatus(ACCOUNT_STATUS);

        Account account = this.converter.toEntity(dto, new Account());
        AccountStatus account_status = this.converter.toEntity(dto, new AccountStatus());

        Assertions.assertEquals(ACCOUNT_ID, account.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, account.getAccountEmail());
        LOGGER.debug(String.format("Account entity[account_id: %d, account_email: %s ];", account.getAccountId(), account.getAccountEmail()));

        Assertions.assertEquals(ACCOUNT_STATUS, account_status.getAccountStatus());
        LOGGER.debug(String.format("AccountStatus entity[account_status: %s];", account_status.getAccountStatus()));


    }

}
