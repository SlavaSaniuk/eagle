package by.bsac.converters;

import by.bsac.core.beans.DtoEntityConverter;
import by.bsac.domain.dto.UserWithContextDto;
import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.webmvc.DtoConversionConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DtoConversionConfiguration.class})
public class UserWithContextDtoIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserWithContextDtoIntegrationTest.class);
    //Spring beans
    @Autowired
    private DtoEntityConverter<UserWithContextDto> TEST;

    @Test
    void toDto_userAndContextEntities_shouldReturnDtoObject() {

        final Integer ID = 23;

        User user = new User();
        user.setUserId(23);
        UserImagesContext context = new UserImagesContext();
        context.setContextId(23);

        UserWithContextDto dto = this.TEST.toDto(user, new UserWithContextDto());
        dto = this.TEST.toDto(context, dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ID, dto.getUserId());
        Assertions.assertEquals(ID, dto.getContextId());

        LOGGER.debug("Resulting dto: " +dto);
    }

    @Test
    void toEntity_UserWithContextDto_shouldReturnUserAndContextEntities() {

        final Integer ID = 24;

        UserWithContextDto dto = new UserWithContextDto();
        dto.setUserId(ID);
        dto.setContextId(24);

        User user = this.TEST.toEntity(dto, new User());
        UserImagesContext context = this.TEST.toEntity(dto, new UserImagesContext());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(ID, user.getUserId());
        LOGGER.debug("User entity: " +user);

        Assertions.assertNotNull(context);
        Assertions.assertEquals(ID, context.getContextId());
        LOGGER.debug("Context entity: " +context);
    }

}
