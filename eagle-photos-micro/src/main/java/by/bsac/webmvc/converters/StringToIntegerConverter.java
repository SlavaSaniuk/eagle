package by.bsac.webmvc.converters;

import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String s) {
        return Integer.valueOf(s);
    }
}
