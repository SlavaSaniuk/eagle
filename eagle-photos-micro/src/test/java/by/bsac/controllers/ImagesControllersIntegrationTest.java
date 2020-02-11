package by.bsac.controllers;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import testconfiguration.TestsAspectsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles({"DATASOURCE_TESTS", "ASPECT_DEBUG"})
@AutoConfigureMockMvc
@Import(TestsAspectsConfiguration.class)
public class ImagesControllersIntegrationTest {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesControllersIntegrationTest.class);

    @Autowired
    private MockMvc mvc;

    @Test
    void getImageByIdInRequestParameters_imageIdIs18_shouldReturnImageData() throws Exception {

        byte[] resp_content =
                this.mvc.perform(get("/images")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("image_id", "18"))
                        .andDo(MockMvcResultHandlers.print(System.out))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                        .andReturn().getResponse().getContentAsByteArray();

        Assertions.assertNotNull(resp_content);
        LOGGER.debug("Response content: " +resp_content);
    }
}
