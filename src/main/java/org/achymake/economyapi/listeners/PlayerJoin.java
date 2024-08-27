package org.achymake.economyapi.listeners;

import org.achymake.economyapi.EconomyAPI;
import org.achymake.economyapi.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    private UpdateChecker getUpdateChecker() {
        return getEconomyAPI().getUpdateChecker();
    }
    public PlayerJoin() {
        getEconomyAPI().getManager().registerEvents(this, getEconomyAPI());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        getUpdateChecker().getUpdate(event.getPlayer());
    }
}