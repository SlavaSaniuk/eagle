package by.bsac.services.parsers;

import java.util.Properties;

public interface PropertiesParser {

    Properties getProperties();

    String getProperty(String property_key);
}
