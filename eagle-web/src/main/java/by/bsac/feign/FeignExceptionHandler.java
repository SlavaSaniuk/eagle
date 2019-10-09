package by.bsac.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Setter
public class FeignExceptionHandler implements ErrorDecoder {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignExceptionHandler.class);
    //Spring beans
    private ObjectMapper object_mapper;

    public Exception decode(String methodKey, Response response) {

        //Decode remote exception by response status:
        switch (response.status()) {
            case 431:

            case 432:
        }

        return null;
    }
}
