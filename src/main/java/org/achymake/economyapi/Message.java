package org.achymake.economyapi;

import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class Message {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    public void send(CommandSender sender, String message) {
        sender.sendMessage(message);
    }
    public void sendLog(Level level, String message) {
        getEconomyAPI().getLogger().log(level, message);
    }
}