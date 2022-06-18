package net.aerh.chatviewer.commands;

import net.aerh.chatviewer.ChatViewerPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.json.JSONObject;
import redis.clients.jedis.JedisPubSub;

public class ChatViewerCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You need to specify a server to view its chat!");
            return false;
        }

        ChatViewerPlugin instance = ChatViewerPlugin.getInstance();
        instance.getExecutor().execute(() ->
                instance.getSubscriber().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String incomingMessage) {
                        if (ChatViewerPlugin.isDebug())
                            instance.getLogger().info("Received Redis message from " + channel + ": " + incomingMessage);

                        JSONObject object = new JSONObject(incomingMessage);
                        String playerName = object.getString("player");
                        String message = object.getString("message");
                        sender.sendMessage(ChatColor.GRAY + "[CV] " + ChatColor.RESET + "[" + channel + "] " + playerName + ": " + message);
                    }
                }, args[0] + "_chat"));
        instance.getListeners().put(sender, args[0]);
        sender.sendMessage(ChatColor.GREEN + "Watching chat in server '" + args[0] + "'");
        return true;
    }
}
