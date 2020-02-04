package by.bsac.controllers;

import by.bsac.configuration.RootConfiguration;
import by.bsac.domain.dto.UserWithContextDto;
import by.bsac.webmvc.WebmvcConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import testconfiguration.TestsAspectsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@SpringBootTest(classes = {RootConfiguration.class, WebmvcConfiguration.class, TestsAspectsConfiguration.class})
@AutoConfigureMockMvc
public class ImagesContextsControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextsControllerIntegrationTest.class);
    //Spring beans
    @Autowired
    private ObjectMapper mapper;

    private MockMvc mock_mvc;

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mock_mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void createUserImagesContext_userWithId2_shouldReturnCreatedUserImagesContextDto() throws Exception {

        final Integer ID = 2;

        UserWithContextDto src_dto = new UserWithContextDto();
        src_dto.setUserId(ID);
        LOGGER.debug("Source DTO: " +src_dto);

        String src_json = this.mapper.writeValueAsString(src_dto);
        LOGGER.debug("Source JSON: " +src_json);

        String TEST_JSON =
                this.mock_mvc.perform(get("/context_create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(src_json)
        ).andExpect(status().is(200)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();

        LOGGER.debug("Test json: " +TEST_JSON);
        UserWithContextDto test_dto = this.mapper.readValue(TEST_JSON, UserWithContextDto.class);

        Assertions.assertNotNull(test_dto);
        Assertions.assertEquals(ID, test_dto.getUserId());
        Assertions.assertEquals(ID, test_dto.getContextId());

        LOGGER.debug("Test dto: " +test_dto);
    }
}
