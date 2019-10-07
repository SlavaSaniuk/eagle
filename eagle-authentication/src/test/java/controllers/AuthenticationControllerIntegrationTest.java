package controllers;

import by.bsac.conf.DatasourcesConfiguration;
import by.bsac.conf.PersistenceConfiguration;
import by.bsac.conf.RootContextConfiguration;
import by.bsac.models.Account;
import by.bsac.models.User;
import by.bsac.services.ServicesConfiguration;
import by.bsac.webmvc.WebmvcConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("TEST")
@SpringJUnitWebConfig({WebmvcConfiguration.class, RootContextConfiguration.class, ServicesConfiguration.class, DatasourcesConfiguration.class, PersistenceConfiguration.class})
public class AuthenticationControllerIntegrationTest {

    private MockMvc mvc;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerIntegrationTest.class);

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void register_newAccount_shouldReturnCreatedUser() throws Exception {

        Account account  = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("12345678");

        ObjectMapper mapper = new ObjectMapper();
        String account_json = mapper.writeValueAsString(account);
        LOGGER.debug("Account JSON: " +account_json);

        String response_body = this.mvc.perform(post("/register")
                .contentType("application/json").characterEncoding("UTF-8").content(account_json)).
                andDo(print()).
                andExpect(status().is(200))
        .andExpect(content().contentType("application/json"))
        .andReturn().getResponse().getContentAsString();

        User user = mapper.readValue(response_body, User.class);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());
    }
}
