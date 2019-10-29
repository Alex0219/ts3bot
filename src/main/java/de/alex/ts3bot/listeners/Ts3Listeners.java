package de.alex.ts3bot.listeners;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import de.alex.ts3bot.Bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ts3Listeners {

    public void startListener() {
        Bootstrap.getInstance().getTs3Connector().getApi().addTS3Listeners(new TS3Listener() {
            public void onTextMessage(TextMessageEvent e) {
                if (e.getMessage().equalsIgnoreCase("!support")) {
                    if (!Bootstrap.getInstance().getTs3Connector().getApi().getClientByUId(e.getInvokerUniqueId()).isInServerGroup(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID")))) {
                        Bootstrap.getInstance().getTs3Connector().getApi().addClientToServerGroup(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID")), Bootstrap.getInstance().getTs3Connector().getApi().getClientByUId(e.getInvokerUniqueId()).getDatabaseId());
                        int currentSupportMembers = Bootstrap.getInstance().getTs3Connector().getApi().getServerGroupClients(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID"))).size();
                        Bootstrap.getInstance().getTs3Connector().getApi().editChannel(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelID")), ChannelProperty.CHANNEL_NAME, Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelOpen") + "[" + currentSupportMembers + "]");
                    } else {
                        Bootstrap.getInstance().getTs3Connector().getApi().removeClientFromServerGroup(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID")), Bootstrap.getInstance().getTs3Connector().getApi().getClientByUId(e.getInvokerUniqueId()).getDatabaseId());
                        int currentSupportMembers = Bootstrap.getInstance().getTs3Connector().getApi().getServerGroupClients(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID"))).size();
                        if (currentSupportMembers == 0) {
                            Bootstrap.getInstance().getTs3Connector().getApi().editChannel(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelID")), ChannelProperty.CHANNEL_NAME, Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelClosed"));

                            //get current clients in channel

                            for (final Client clients : Bootstrap.getInstance().getTs3Connector().getApi().getClients()) {
                                if (clients.getChannelId() == Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelID"))) {
                                    Bootstrap.getInstance().getTs3Connector().getApi().kickClientFromChannel(clients.getId());
                                }
                            }

                        } else {
                            Bootstrap.getInstance().getTs3Connector().getApi().editChannel(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelID")), ChannelProperty.CHANNEL_NAME, Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelOpen") + "[" + currentSupportMembers + "]");
                        }

                    }
                }
            }

            public void onServerEdit(ServerEditedEvent e) {
            }

            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
            }

            public void onClientMoved(ClientMovedEvent e) {
                int id = e.getTargetChannelId();
                if (id == Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportChannelID"))) {
                    for (Client clients : Bootstrap.getInstance().getTs3Connector().getApi().getClients()) {
                        if (clients.isInServerGroup(Integer.parseInt(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroupID")))) {
                            Bootstrap.getInstance().getTs3Connector().getApi().sendPrivateMessage(clients.getId(), Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("UserNeedsHelp"));
                        }
                    }
                }
            }

            public void onClientLeave(ClientLeaveEvent e) {

            }

            public void onClientJoin(ClientJoinEvent e) {
                List<Integer> supportMembers = Arrays.stream(Bootstrap.getInstance().getPropertyHandler().getProperties().getProperty("SupportGroups").split(",")).map(Integer::parseInt).collect(Collectors.toList());

                for (final ServerGroup serverGroup : Bootstrap.getInstance().getTs3Connector().getApi().getServerGroupsByClientId(Bootstrap.getInstance().getTs3Connector().getApi().getClientByUId(e.getUniqueClientIdentifier()).getDatabaseId())) {
                    if (supportMembers.contains(serverGroup.getId())) {
                        Bootstrap.getInstance().getTs3Connector().getApi().sendPrivateMessage(e.getClientId(), "Hi! Schreibe mir !support um die Suppprtgruppe zu erhalten!");
                        break;
                    }
                }

            }

            public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
            }

            public void onChannelMoved(ChannelMovedEvent e) {
            }

            public void onChannelEdit(ChannelEditedEvent e) {
            }

            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
            }

            public void onChannelDeleted(ChannelDeletedEvent e) {
            }

            public void onChannelCreate(ChannelCreateEvent e) {
            }
        });
    }
}
