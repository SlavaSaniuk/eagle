package feign;

import by.bsac.conf.RootContextConfiguration;
import by.bsac.feign.FeignClientsConfiguration;
import by.bsac.feign.FeignConfiguration;
import by.bsac.feign.clients.UserDetailsService;
import by.bsac.models.UserName;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({RootContextConfiguration.class, FeignConfiguration.class, FeignClientsConfiguration.class})
public class UserDetailsServiceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceIntegrationTest.class);

    //Spring beans
    @Autowired
    private UserDetailsService uds;

    @Test
    public void createDetails_existedUser_shouldReturnCreatedDto() {

        UserWithDetailsDto dto = new UserWithDetailsDto();

        dto.setUserId(8);
        dto.setUserName(new UserName("TEST_FNAME", "TEST-LNAME"));

        dto = this.uds.createDetails(dto);

        Assertions.assertNotNull(dto);
        LOGGER.debug("RESPONSE_DTO: " +dto.toString());

    }
}
