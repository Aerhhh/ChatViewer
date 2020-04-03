package me.aerh.chatviewer;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

public class ChatViewerPlugin extends JavaPlugin {
    private static ChatViewerPlugin instance;
    private Jedis publisher;
    private Jedis subscriber;

    @Override
    public void onEnable() {
        instance = this;

        if(getConfig().get("auth.redis") != null) {
            publisher = new Jedis(getConfig().getString("auth.redis.host"), getConfig().getInt("auth.redis.port"));
            subscriber = new Jedis(getConfig().getString("auth.redis.host"), getConfig().getInt("auth.redis.port"));
        } else {
            publisher = new Jedis();
            subscriber = new Jedis();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public Jedis getPublisher() {
        return publisher;
    }

    public Jedis getSubscriber() {
        return subscriber;
    }

    public static ChatViewerPlugin getInstance() {
        return instance;
    }
}
