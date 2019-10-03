package by.bsac.services.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileParser implements PropertiesParser {

    private Properties props = new Properties();

    public PropertiesFileParser(File a_property_file) throws IOException {
        this.props.load(new FileInputStream(a_property_file));
    }

    public PropertiesFileParser(InputStream a_stream) throws IOException {
        this.props.load(a_stream);
    }

    public Properties getProperties() {
        if (props == null) throw new NullPointerException("Properties file not load.");
        return this.props;
    }

    public String getProperty(String property_key) {
        if (props == null) throw new NullPointerException("Properties file not load.");
        if (property_key == null ) throw new NullPointerException("Property key string is null.");
        return this.props.getProperty(property_key);
    }
}
