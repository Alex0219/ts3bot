package de.alex.ts3bot;

import de.alex.ts3bot.connection.Ts3Connector;
import de.alex.ts3bot.listeners.Ts3Listeners;
import de.alex.ts3bot.properties.PropertyHandler;

public class Bootstrap {

    public static Bootstrap instance;

    PropertyHandler propertyHandler;
    Ts3Connector ts3Connector;
    Ts3Listeners ts3Listeners;

    public Bootstrap() {
        instance = this;
        propertyHandler = new PropertyHandler();
        ts3Connector = new Ts3Connector();
        propertyHandler.createPropertyFile();
        propertyHandler.loadPropertyFile();
        ts3Connector.connect();
        ts3Listeners = new Ts3Listeners();
        ts3Listeners.startListener();
    }

    public static void main(String[] args) {
        new Bootstrap();
    }

    public static Bootstrap getInstance() {
        return instance;
    }

    public PropertyHandler getPropertyHandler() {
        return propertyHandler;
    }

    public Ts3Connector getTs3Connector() {
        return ts3Connector;
    }
}
