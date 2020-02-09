package by.bsac.webmvc.converters;

import org.springframework.core.convert.converter.Converter;

/**
 * Spring {@link Converter} can convert {@link String}
 * request parameter to {@link Integer} parameters.
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String s) {
        return Integer.valueOf(s);
    }
}
