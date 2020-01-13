package by.bsac.services;

import by.bsac.annotations.debug.MethodCall;
import by.bsac.annotations.debug.MethodExecutionTime;
import by.bsac.aspects.TestsAspectsConfiguration;
import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.models.Account;
import by.bsac.services.accounts.AccountsCrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles({"TEST", "ASPECTS_DEBUG"})
@SpringJUnitConfig(classes = {DatasourcesConfiguration.class, PersistenceConfiguration.class,
        ServicesConfiguration.class, TestsAspectsConfiguration.class})
@Sql("classpath:/repositories/account-imports.sql")
public class AccountCrudServiceIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCrudServiceIntegrationTest.class);
    //Spring beans
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountsCrudService acs;

    @Test
    @MethodCall(withStartTime = true)
    @MethodExecutionTime(inMicros = true, inMillis = true)
    void getAccountByEmail_accountWithEmailAlreadyRegistered_shouldReturnAccountEntity() {

        final String ACCOUNT_EMAIL = "test1@mail";
        Account founded = this.acs.getAccountByEmail(ACCOUNT_EMAIL);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(1, founded.getAccountId());
        Assertions.assertEquals(ACCOUNT_EMAIL, founded.getAccountEmail());

        LOGGER.debug("Founded account: " +founded.toString());
    }

}
