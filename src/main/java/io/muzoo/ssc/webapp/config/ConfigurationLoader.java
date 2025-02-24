package io.muzoo.ssc.webapp.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {

    public static ConfigProperties loadConfiguration() {
        String configFileName = "config.properties";
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(configFileName)) {
            prop.load(input);

            String driverClassName = prop.getProperty("database.driverClassName");
            String connectionUrl = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            ConfigProperties configProperties = new ConfigProperties();
            configProperties.setDatabaseDriverClassName(driverClassName);
            configProperties.setDatabaseConnectionUrl(connectionUrl);
            configProperties.setDatabaseUsername(username);
            configProperties.setDatabasePassword(password);

            return configProperties;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Or throw a custom exception if needed
        }
    }
}
