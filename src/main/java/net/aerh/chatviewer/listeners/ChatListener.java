package net.aerh.chatviewer.listeners;

import net.aerh.chatviewer.ChatViewerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.json.JSONObject;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        JSONObject object = new JSONObject();
        object.put("player", player.getName());
        object.put("message", message);
        ChatViewerPlugin.getInstance().getPublisher().publish(
                ChatViewerPlugin.getInstance().getConfig().getString("serverId") + "_chat",
                object.toString()
        );
    }
}
