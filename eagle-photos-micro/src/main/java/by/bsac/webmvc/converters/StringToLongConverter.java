package by.bsac.webmvc.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.Convert;
/**
 * Spring {@link Converter} can convert {@link String}
 * request parameter to {@link Long} parameters.
 */
public class StringToLongConverter implements Converter<String, Long> {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(StringToLongConverter.class);

    @Override
    public Long convert(String s) {
        return Long.valueOf(s);
    }
}
