package by.bsac.webmvc.controllers;

import by.bsac.domain.dto.ContextWithImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import static by.bsac.core.logging.SpringCommonLogging.*;

@Controller("ImagesController")
public class ImagesController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);

    public ImagesController() {
        LOGGER.debug(CREATION.startCreateBean(BeanDefinition.of(ImagesController.class)));
    }

    @ResponseBody
    public ContextWithImageDto uploadImage(@RequestBody ContextWithImageDto dto) {

        return dto;
    }
}
