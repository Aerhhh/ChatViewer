package me.aerh.chatviewer.listeners;

import me.aerh.chatviewer.ChatViewerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        ChatViewerPlugin.getInstance().getPublisher().publish(ChatViewerPlugin.getInstance().getConfig().getString("serverId") + "_chat", "{\"message\":\"" + event.getFormat() + "\"}");
    }
}
