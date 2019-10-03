package by.bsac.services.parsers;

import by.bsac.utilities.ClazzUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class TypesPropertiesParser extends PropertiesFileParser {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TypesPropertiesParser.class);

    public TypesPropertiesParser(File a_property_file) throws IOException {
        super(a_property_file);
    }

    public TypesPropertiesParser(InputStream a_stream) throws IOException {
        super(a_stream);
    }

    @SuppressWarnings("unchecked")
    public Object getTypeProperty(String property_key, Class primitive_wrapper) {

        if (primitive_wrapper == null) throw new NullPointerException("Primitive wrapper class is null.");
        if (!ClazzUtils.isPrimitiveWrapper(primitive_wrapper)) throw new IllegalArgumentException("Parameter [primitive_wrapper] is not wrapper class of primitive type.");

        String property_value = super.getProperty(property_key);

        if (primitive_wrapper.equals(Character.class)) return property_value.charAt(0);

        try {
            return primitive_wrapper.getConstructor(String.class).newInstance(property_value);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            LOGGER.error("This exception should not been thrown.");
            e.printStackTrace();
        }

        return null;
    }


}
