package by.bsac.dto;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DtoConvertersConfiguration.class})
public class UserWithDetailsDtoConverterIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithDetailsDtoConverterIntegrationTest.class);

    @Autowired
    private EmbeddedDeConverter<UserWithDetailsDto> CONVERTER;

    @Test
    public void toDto_userAndDetailsEntities_shouldReturnUserWithDetailsDto() {

        final Integer USER_ID = 23;
        final String USER_ALIAS = "TEST-ALIAS";

        final Integer DETAILS_ID = 24;
        final String DETAILS_FNAME = "TEST-FNAME";
        final String DETAILS_LNAME = "TEST-LNAME";


        User user = new User();
        user.setUserId(USER_ID);
        user.setUserIdAlias(USER_ALIAS);

        UserDetails details = new UserDetails();
        details.setDetailId(DETAILS_ID);
        details.setUserName(new UserName(DETAILS_FNAME, DETAILS_LNAME));

        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto = this.CONVERTER.toDto(user, dto);
        dto = this.CONVERTER.toDto(details, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(USER_ID, dto.getUserId());
        Assertions.assertEquals(USER_ALIAS, dto.getUserAlias());
        Assertions.assertEquals(DETAILS_ID, dto.getDetailId());
        Assertions.assertEquals(DETAILS_FNAME, dto.getFirstName());
        Assertions.assertEquals(DETAILS_LNAME, dto.getLastName());

        LOGGER.debug("DTO: " +dto.toString());
        LOGGER.debug("User: " +user.toString());
        LOGGER.debug("Details: " +details.toString());

    }

    @Test
    public void toEntity_userWithDetailsDto_shouldReturnUserAndDetailsEntities() {

        final Integer USER_ID = 23;
        final String USER_ALIAS = "TEST-ALIAS";

        final Integer DETAILS_ID = 24;
        final String DETAILS_FNAME = "TEST-FNAME";
        final String DETAILS_LNAME = "TEST-LNAME";

        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(USER_ID);
        dto.setUserAlias(USER_ALIAS);
        dto.setDetailId(DETAILS_ID);
        dto.setFirstName(DETAILS_FNAME);
        dto.setLastName(DETAILS_LNAME);

        User user = new User();
        user = this.CONVERTER.toEntity(dto, user);

        UserDetails details = new UserDetails();
        details = this.CONVERTER.toEntity(dto, details, new UserName());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(USER_ID, user.getUserId());
        Assertions.assertEquals(USER_ALIAS, user.getUserIdAlias());

        Assertions.assertNotNull(details);
        Assertions.assertEquals(DETAILS_ID, details.getDetailId());
        Assertions.assertEquals(DETAILS_FNAME, details.getUserName().getFirstName());
        Assertions.assertEquals(DETAILS_LNAME, details.getUserName().getLastName());

        LOGGER.debug("DTO: " +dto.toString());
        LOGGER.debug("User: " +user.toString());
        LOGGER.debug("Details: " +details.toString());

    }
}
