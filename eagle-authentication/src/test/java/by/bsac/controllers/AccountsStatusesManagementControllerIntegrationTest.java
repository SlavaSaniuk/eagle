package by.bsac.controllers;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.conf.RootContextConfiguration;
import by.bsac.models.Status;
import by.bsac.services.ServicesConfiguration;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import by.bsac.webmvc.dto.AccountWithStatusDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("TEST")
@Sql("classpath:/repositories/account-imports.sql" )
@SpringJUnitWebConfig(classes = {WebmvcConfiguration.class, DtoConvertersConfiguration.class, ServicesConfiguration.class,
        RootContextConfiguration.class, PersistenceConfiguration.class, DatasourcesConfiguration.class})
public class AccountsStatusesManagementControllerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsStatusesManagementControllerIntegrationTest.class);
    private MockMvc mock_mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mock_mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
    }

    @Test
    void confirmAccount_createdAccount_shouldConfirmAccountAndReturnThem() throws Exception {

        final Integer ACCOUNT_ID = 2;
        AccountWithStatusDto dto = new AccountWithStatusDto();
        dto.setAccountId(ACCOUNT_ID);
        LOGGER.debug("Account DTO: " +dto.toString());

        String account_dto_json = this.mapper.writeValueAsString(dto);
        LOGGER.debug("Account DTO JSON: " +account_dto_json);

        String result = this.mock_mvc.perform(get("/confirm_account")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(account_dto_json))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        LOGGER.debug("Result DTO JSON: " +result);

        AccountWithStatusDto result_dto = this.mapper.readValue(result, AccountWithStatusDto.class);
        LOGGER.debug("Result DTO: " +result_dto.toString());

        Assertions.assertEquals(Status.CONFIRMED, result_dto.getAccountStatus());


    }
}
