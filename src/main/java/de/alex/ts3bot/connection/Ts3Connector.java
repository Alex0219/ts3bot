package de.alex.ts3bot.connection;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.alex.ts3bot.Bootstrap;

public class Ts3Connector {

    public TS3Config config = new TS3Config();
    public TS3Query query = new TS3Query(config);
    public TS3Api api = query.getApi();

    public Ts3Connector() {
    }

    /**
     * Connect to the target ts3 server
     */
    public void connect() {
        config.setHost(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Host"));
        query.connect();
        api.login(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Username"), Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Password"));
        api.selectVirtualServerByPort(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Port")));
        if (!api.whoAmI().getNickname().equalsIgnoreCase(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Nickname"))) {
            api.setNickname(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("Nickname"));
        }
        api.registerAllEvents();
    }
    public TS3Config getConfig() {
        return config;
    }

    public TS3Query getQuery() {
        return query;
    }

    public TS3Api getApi() {
        return api;
    }
}
