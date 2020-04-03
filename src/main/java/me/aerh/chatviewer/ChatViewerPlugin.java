package me.aerh.chatviewer;

import me.aerh.chatviewer.commands.ChatViewerCommand;
import me.aerh.chatviewer.listeners.ChatListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatViewerPlugin extends JavaPlugin {
    private static ChatViewerPlugin instance;
    private Map<CommandSender, String> listeners = new HashMap<>();
    ExecutorService executor = Executors.newFixedThreadPool(5);
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

        getCommand("viewchat").setExecutor(new ChatViewerCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
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

    public Map<CommandSender, String> getListeners() {
        return listeners;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}
