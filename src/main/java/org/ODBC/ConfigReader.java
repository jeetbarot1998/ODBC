package org.ODBC;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConfigReader{
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }


}