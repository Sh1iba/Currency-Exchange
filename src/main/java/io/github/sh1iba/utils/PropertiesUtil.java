package io.github.sh1iba.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("File application.properties is not found");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties", e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null){
            throw new RuntimeException("Property key is not found");
        }
        return value;
    }

}
