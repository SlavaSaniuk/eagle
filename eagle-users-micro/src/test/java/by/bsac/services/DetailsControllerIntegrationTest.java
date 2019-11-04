package by.bsac.services;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.core.DEConverter;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.repositories.UserRepository;
import by.bsac.webmvc.WebMvcConfiguration;
import by.bsac.webmvc.dto.UserWithDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("TEST")
@SpringBootTest(classes = {DatasourcesConfig.class, ServicesConfiguration.class, WebMvcConfiguration.class})
@EnableAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class DetailsControllerIntegrationTest {

    //Test constants
    private final String USER_ID_ALIAS = "details-controller-test";
    private final String CONTROLLER_URL = "/details_create";
    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsControllerIntegrationTest.class);

    //Spring beans
    @Autowired
    private MockMvc mock_mvc;

    @Autowired
    private UserRepository user_repo;

    @Autowired
    private ObjectMapper mapper;



    @Test
    @Order(1)
    @Transactional
    @Commit
    @Disabled
    public void setUpBeforeAll() {
        User user = new User();
        user.setUserIdAlias(this.USER_ID_ALIAS);

        user_repo.save(user);
    }

    @Test
    @Order(2)
    @Commit
    public void createDetails_registeredUser_shouldCreateNewDetails() throws Exception {

        // input parameters
        //User user = this.user_repo.findByUserIdAlias(this.USER_ID_ALIAS);
        //Assertions.assertNotNull(user);
        User user = new User();
        user.setUserId(1);

        final String FNAME = "Test";
        final String LNAME = "Admin";
        UserDetails details = new UserDetails();
        details.setUserName(new UserName(FNAME, LNAME));

        //Create DTO
        UserWithDetailsDto dto = DEConverter.toDto(user, new UserWithDetailsDto());
        DEConverter.toDto(details, dto);

        String dto_json = mapper.writeValueAsString(dto);
        LOGGER.debug("DTO JSON: " +dto_json);

        //try to create user details
        String result_content = this.mock_mvc.perform(MockMvcRequestBuilders.post(CONTROLLER_URL)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dto_json)
        ).andExpect(status().is(200))
      //  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString();

        LOGGER.debug("Response JSON: " +result_content);

        UserDetails details_from_json = mapper.readValue(result_content, UserDetails.class);

        Assertions.assertNotNull(details_from_json);
        Assertions.assertNotNull(details_from_json.getDetailId());

        Assertions.assertEquals(details.getUserName().getFirstName(), details_from_json.getUserName().getFirstName());



    }

}
