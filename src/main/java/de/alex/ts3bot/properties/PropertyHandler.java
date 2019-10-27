package de.alex.ts3bot.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {

    Properties properties = new Properties();

    public void createPropertyFile() {

        String appConfigPath = "ts3bot.properties";

        if (!new File(appConfigPath).exists()) {
            try {
                new File(appConfigPath).createNewFile();
                properties.load(new FileInputStream(appConfigPath));
                properties.setProperty("Host", "0.0.0.0");
                properties.setProperty("Port", "9987");
                properties.setProperty("vServerID", "1");
                properties.setProperty("SupportGroupID", "1");
                properties.setProperty("SupportChannelID", "1");
                properties.setProperty("Nickname", "Support-Bot");
                properties.setProperty("Username", "serveradmin");
                properties.setProperty("Password", "abc");
                properties.store(new FileWriter(appConfigPath), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPropertyFile() {
        String appConfigPath = "ts3bot.properties";
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
