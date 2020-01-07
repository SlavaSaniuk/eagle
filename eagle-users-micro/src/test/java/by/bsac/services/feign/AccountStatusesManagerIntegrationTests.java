package by.bsac.services.feign;

import by.bsac.aspects.TestAspectsConfiguration;
import by.bsac.configuration.DatasourcesConfig;
import by.bsac.models.AccountStatus;
import by.bsac.services.ServicesConfiguration;
import by.bsac.services.feign.clients.AccountsStatusesManager;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.WebMvcConfiguration;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"TEST", "ASPECTS_DEBUG"})
@SpringBootTest(classes = {DatasourcesConfig.class, ServicesConfiguration.class, FeignConfiguration.class, TestAspectsConfiguration.class, WebMvcConfiguration.class, DtoConvertersConfiguration.class})
@EnableAutoConfiguration
@EntityScan("by.bsac.models")
public class AccountStatusesManagerIntegrationTests {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountStatusesManagerIntegrationTests.class);

    //Spring beans
    @Autowired
    private AccountsStatusesManager asm;

    @Test
    public void confirmAccount_accountWithId3_shouldConfirmAccount() {
        AccountWithStatusDto dto = new AccountWithStatusDto();
        final Integer ACCOUNT_ID = 3;
        dto.setAccountId(ACCOUNT_ID);

        AccountWithStatusDto result = this.asm.confirmAccount(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ACCOUNT_ID, result.getAccountId());
        Assertions.assertEquals(AccountStatus.AccountStatuses.CONFIRMED, result.getAccountStatus());

        LOGGER.debug("AccountWithStatusDto: " +result.toString());
    }

}
