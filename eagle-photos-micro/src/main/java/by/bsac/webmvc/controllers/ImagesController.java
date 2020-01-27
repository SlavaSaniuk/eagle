package by.bsac.webmvc.controllers;

import by.bsac.domain.dto.ContextWithImageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller("ImagesController")
public class ImagesController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);

    public ContextWithImageDto uploadImage(ContextWithImageDto dto) {

        return dto;
    }
}
