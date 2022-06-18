package net.aerh.chatviewer;

import net.aerh.chatviewer.commands.ChatViewerCommand;
import net.aerh.chatviewer.listeners.ChatListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatViewerPlugin extends JavaPlugin {

    private static ChatViewerPlugin instance;
    private final Map<CommandSender, String> listeners = new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private Jedis publisher;
    private Jedis subscriber;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        String host = getConfig().getString("auth.redis.host", "localhost");
        int port = getConfig().getInt("auth.redis.port", 6379);
        publisher = new Jedis(host, port);
        subscriber = new Jedis(host, port);

        String username = getConfig().getString("auth.redis.username");
        String password = getConfig().getString("auth.redis.password");
        if (username != null && password != null) {
            publisher.auth(username, password);
            subscriber.auth(username, password);
        }

        getCommand("viewchat").setExecutor(new ChatViewerCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public static boolean isDebug() {
        return instance.getConfig().getBoolean("debug");
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
