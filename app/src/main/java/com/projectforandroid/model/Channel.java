package com.projectforandroid.model;

/**
 * Created by exkulo on 9/24/2015.
 */
public class Channel {

    String channelId;
    String name;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Channel{" +
            "channelId='" + channelId + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
