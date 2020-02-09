package by.bsac.controllers;

import by.bsac.domain.dto.UserWithContextDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import testconfiguration.TestsAspectsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@AutoConfigureMockMvc
@Import(TestsAspectsConfiguration.class)
public class ImagesContextsControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesContextsControllerIntegrationTest.class);
    //Spring beans
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Disabled
    public void createImagesContext_newUser_shouldReturnCreatedContext() throws Exception {

        final Integer ID = 1;
        UserWithContextDto src_dto = new UserWithContextDto();
        src_dto.setUserId(ID);
        LOGGER.debug("Source DTO: " +src_dto);

        String src_json = this.mapper.writeValueAsString(src_dto);
        LOGGER.debug("Source JSON: " +src_json);

        String res_json = this.mvc.perform(post("/context_create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(src_json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        LOGGER.debug("Resulting JSON: " +res_json);

        UserWithContextDto res_dto = this.mapper.readValue(res_json, UserWithContextDto.class);
        LOGGER.debug("Resulting DTO: " +res_json);

        Assertions.assertNotNull(res_dto);
        Assertions.assertEquals(ID, res_dto.getContextId());

    }

    @Test
    public void getUserImagesContext_contextId_shouldReturnContextEntity() throws Exception {

        final Integer ID = 2;

        UserWithContextDto src_dto = new UserWithContextDto();
        src_dto.setContextId(ID);
        LOGGER.debug("Source DTO: " +src_dto);

        String src_json = this.mapper.writeValueAsString(src_dto);
        LOGGER.debug("Source JSON: " +src_json);

        String res_json = this.mvc.perform(post("/context_get")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(src_json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +res_json);

        UserWithContextDto dto = this.mapper.readValue(res_json, UserWithContextDto.class);
        LOGGER.debug("resulting DTO: " +dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ID, dto.getContextId());

    }

    @Test
    public void getUserImagesContextById_newUser_shouldReturnContextEntity() throws Exception {

        final Integer ID = 2;

        String res_json = this.mvc.perform(get("/context_getById")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("context_id", ID.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +res_json);

        UserWithContextDto dto = this.mapper.readValue(res_json, UserWithContextDto.class);
        LOGGER.debug("resulting DTO: " +dto);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(ID, dto.getContextId());

    }
}
