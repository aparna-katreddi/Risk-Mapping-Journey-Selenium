package com.ai.holistic.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        String env = System.getProperty("env", "qa"); // default to qa
        String filePath = "src/test/resources/config." + env + ".properties";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            properties.load(fis);
            log.info("Loaded config file: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to load config file: {}", filePath, e);
            throw new RuntimeException("Couldn't load config.properties");
        }
    }

//    public static String get(String key) {
//        return props.getProperty(key);
//    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        // Override from system properties if set
        String systemProperty = System.getProperty(key);
        return systemProperty != null ? systemProperty : value;
    }
}

