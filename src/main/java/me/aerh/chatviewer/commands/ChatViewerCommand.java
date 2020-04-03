package me.aerh.chatviewer.commands;

import me.aerh.chatviewer.ChatViewerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONObject;
import redis.clients.jedis.JedisPubSub;

import java.util.logging.Level;

public class ChatViewerCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You need to specify a server to view its chat!");
            return false;
        }

        ChatViewerPlugin.getInstance().getExecutor().execute(() ->
                ChatViewerPlugin.getInstance().getSubscriber().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String incomingMessage) {
                        Bukkit.getLogger().log(Level.INFO, "Redis Channel: " + channel + ", Message: " + incomingMessage);
                        // message = json string
                        JSONObject object = new JSONObject(incomingMessage);
                        String message = object.getString("message");
                        // when a message is received from the other server
                        sender.sendMessage(ChatColor.GRAY + "[V] " + ChatColor.RESET + "[" + channel + "] " + ChatColor.RESET + message);
                    }
                }, args[0] + "_chat"));
        ChatViewerPlugin.getInstance().getListeners().put(sender, args[0]);
        sender.sendMessage(ChatColor.GREEN + "Watching chat in server '" + args[0] + "'");
        return false;
    }
}
