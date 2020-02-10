package by.bsac.controllers;

import by.bsac.domain.ImageExtension;
import by.bsac.domain.dto.ContextWithImageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import testconfiguration.TestsAspectsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@AutoConfigureMockMvc
@Import(TestsAspectsConfiguration.class)
public class ImagesFilesControllerIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesFilesControllerIntegrationTest.class);

    //Spring beans
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void uploadImage_newUserImage_shouldCreateImageFileEntity() throws Exception {

        final Integer CONTEXT_USER_ID = 2;
        final byte[] IMAGE_DATA = {10, 20, 30, 40};
        final String IMAGE_NAME = "Any name";

        ContextWithImageDto src_dto = new ContextWithImageDto();
        src_dto.setContextId(CONTEXT_USER_ID);

        src_dto.setImageExtension(ImageExtension.JPG);
        src_dto.setImageData(IMAGE_DATA);
        src_dto.setImageName(IMAGE_NAME);

        LOGGER.debug("Source DTO: " +src_dto);

        String src_json = this.mapper.writeValueAsString(src_dto);
        LOGGER.debug("Source JSON: " +src_json);

        String res_json = this.mvc.perform(post("/image_file_upload")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(src_json))
                .andDo(print(System.out))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +res_json);

        ContextWithImageDto res_dto = this.mapper.readValue(res_json, ContextWithImageDto.class);

        Assertions.assertNotNull(res_dto);
        Assertions.assertEquals(CONTEXT_USER_ID, res_dto.getContextId());
        Assertions.assertNotNull(res_dto.getImageId());
        Assertions.assertNotEquals(IMAGE_NAME, res_dto.getImageName());

        LOGGER.debug("Result DTO: " +res_dto);


    }
}
