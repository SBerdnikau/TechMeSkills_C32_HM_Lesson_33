package com.teachmeskills.lesson_33.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnect {
    private static final Logger logger = Logger.getLogger(DatabaseConnect.class.getName());
    private static volatile DatabaseConnect instance;
    private String url;
    private String user;
    private String password;

    private DatabaseConnect() {
        loadProperties();
    }

    public static DatabaseConnect getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnect.class) {
                if (instance == null) {
                    instance = new DatabaseConnect();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error when uploading the configuration file -> " + ex.getMessage(), ex);
        }
    }

    public String getUser () {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
