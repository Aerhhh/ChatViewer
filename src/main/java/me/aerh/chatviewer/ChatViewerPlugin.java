package me.aerh.chatviewer;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

public class ChatViewerPlugin extends JavaPlugin {
    private static ChatViewerPlugin instance;
    private Jedis jedis;


    @Override
    public void onEnable() {
        instance = this;

        if(getConfig().get("auth.redis") != null) {
            jedis = new Jedis(getConfig().getString("auth.redis.host"), getConfig().getInt("auth.redis.port"));
        } else {
            jedis = new Jedis();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public static ChatViewerPlugin getInstance() {
        return instance;
    }
}
