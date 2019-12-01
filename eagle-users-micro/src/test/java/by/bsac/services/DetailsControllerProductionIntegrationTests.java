package by.bsac.services;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.webmvc.DtoConvertersConfiguration;
import by.bsac.webmvc.WebMvcConfiguration;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {DatasourcesConfig.class, ServicesConfiguration.class,
        WebMvcConfiguration.class, DtoConvertersConfiguration.class})
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("DEVELOPMENT")
public class DetailsControllerProductionIntegrationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsControllerProductionIntegrationTests.class);
    private static final String CONTROLLER_URL = "/details_create";

    //Spring Beans
    @Autowired
    private MockMvc mock_mvc;
    @Autowired
    private ObjectMapper object_mapper; //From WebMvcConfiguration
    @Autowired
    private EmbeddedDeConverter<UserWithDetailsDto> converter; //From DtoConvertersConfiguration

    @Test
    public void createDetails_userWithDetailsDto_shouldReturnCreatedUserWithDetailsDto() throws Exception {

        final Integer expected_user_id = 1;
        final String expected_fname = "FNAME";
        final String expected_lname = "LNAME";

        //Create user and details entity
        //Not in persistence context
        User user = new User();
        user.setUserId(expected_user_id);

        UserDetails details = new UserDetails();
        details.setUserName(new UserName(expected_fname, expected_lname));

        //Create DTO
        UserWithDetailsDto dto = this.converter.toDto(user, new UserWithDetailsDto());
        dto = this.converter.toDto(details, dto);

        //Create json
        final String REQUEST_JSON = this.object_mapper.writeValueAsString(dto);
        LOGGER.debug("REQUEST JSON: " +REQUEST_JSON);

        //Create details
        final String RESPONSE_JSON = this.mock_mvc.perform(post(CONTROLLER_URL)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(REQUEST_JSON))
                .andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        LOGGER.debug("RESPONSE_JSON" +RESPONSE_JSON);
        UserWithDetailsDto response_dto = this.object_mapper.readValue(RESPONSE_JSON, UserWithDetailsDto.class);

        Assertions.assertNotNull(response_dto);
        User response_user = this.converter.toEntity(response_dto, new User());
        UserDetails response_details = this.converter.toEntity(response_dto, new UserDetails(), new UserName());

        Assertions.assertEquals(expected_user_id, response_user.getUserId());
        Assertions.assertEquals(expected_fname, response_details.getUserName().getFirstName());
        Assertions.assertEquals(expected_lname, response_details.getUserName().getLastName());

    }



}
