package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();
    private static PropertyReaderUtil INSTANCE;

    public static PropertyReaderUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropertyReaderUtil();
            loadProperties();
        }
        return INSTANCE;
    }

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    private static void loadProperties() {
        final String propertiesFileName = "C:\\Users\\danko\\IdeaProjects\\jwd-core-final\\src\\main\\resources\\application.properties";

//        ClassLoader classLoader = PropertyReaderUtil.class.getClassLoader();

        try (InputStream inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }
}
