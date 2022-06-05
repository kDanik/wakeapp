package de.wakeapp.service.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static final String API_PROPERTIES_NAME = "my-api.properties";

    public static Properties readAll(String name) throws PropertiesNotFoundException {
        try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(name)) {

            Properties properties = new Properties();

            if (input == null) {
                System.out.println("Error, unable to find " + name);

                throw new PropertiesNotFoundException("Unable to find file with name" + name);
            }

            // load a properties file from class path, inside static method
            properties.load(input);

            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();

            throw new PropertiesNotFoundException("IOException while trying to read properties " + name, ex);
        }
    }
}
