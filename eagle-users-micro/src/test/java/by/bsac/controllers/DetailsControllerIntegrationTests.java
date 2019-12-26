package by.bsac.controllers;

import by.bsac.configuration.DatasourcesConfig;
import by.bsac.exceptions.NoCreatedDetailsException;
import by.bsac.models.User;
import by.bsac.models.UserDetails;
import by.bsac.models.UserName;
import by.bsac.repositories.UserRepository;
import by.bsac.services.DetailsManager;
import by.bsac.services.ServicesConfiguration;
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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("TEST")
@SpringBootTest(classes = {ServicesConfiguration.class, DatasourcesConfig.class,
        WebMvcConfiguration.class, DtoConvertersConfiguration.class})
@EnableAutoConfiguration
@AutoConfigureMockMvc
@EntityScan("by.bsac.models")
public class DetailsControllerIntegrationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsControllerIntegrationTests.class);
    //Spring beans
    @Autowired
    private MockMvc mock_mvc;
    @Autowired
    private UserRepository user_repository;
    @Autowired
    private DetailsManager details_service;
    @Autowired
    private ObjectMapper jackson_mapper;

    @Test
    public void getDetails_persistedUser_shouldReturnUserWithDetailsDto() throws Exception {

        //Persist details before
        User user = new User();
        user = this.user_repository.save(user);

        UserDetails details = new UserDetails();
        details.setUserName(new UserName("TEST", "TEST2"));

        UserDetails persisted = this.details_service.createDetails(user, details);

        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(user.getUserId());
        String dto_json = this.jackson_mapper.writeValueAsString(dto);
        LOGGER.debug("REQUEST JSON: " +dto_json);

        //Perform request
        String json_response =
                this.mock_mvc.perform(post("/details_get")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(dto_json)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();
        LOGGER.debug("RESPONSE JSON: " +json_response);

        UserWithDetailsDto response_dto = this.jackson_mapper.readValue(json_response, UserWithDetailsDto.class);

        Assertions.assertNotNull(response_dto);
        Assertions.assertEquals(persisted.getDetailId(), response_dto.getDetailId());
    }

    @Test
    public void getDetails_persistedUserWithoutDetails_shouldReturn441HttpStatusCode() throws Exception {

        //Persist details before
        User user = new User();
        user = this.user_repository.save(user);

        UserWithDetailsDto dto = new UserWithDetailsDto();
        dto.setUserId(user.getUserId());

        String dto_json = this.jackson_mapper.writeValueAsString(dto);
        LOGGER.debug("REQUEST JSON: " +dto_json);

        //Perform request
        String json_response =
                this.mock_mvc.perform(post("/details_get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(dto_json)
                ).andExpect(
                        status().is(441)
                ).andDo(MockMvcResultHandlers.print(System.out)
                ).andReturn().getResponse().getContentAsString();

        LOGGER.debug("RESPONSE JSON: " +json_response);

        Exception exception = this.jackson_mapper.readValue(json_response, Exception.class);
        LOGGER.debug("Exception message: " +exception.getMessage());

    }
}
