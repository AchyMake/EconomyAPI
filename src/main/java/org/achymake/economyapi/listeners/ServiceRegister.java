package org.achymake.economyapi.listeners;

import org.achymake.economyapi.EconomyAPI;
import org.achymake.economyapi.providers.EconomyProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;

public class ServiceRegister implements Listener {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    public ServiceRegister() {
        getEconomyAPI().getManager().registerEvents(this, getEconomyAPI());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onServiceRegister(ServiceRegisterEvent event) {
        var rsp = event.getProvider();
        if (rsp.getProvider() instanceof EconomyProvider economyProvider) {
            var plugin = rsp.getPlugin();
            if (!economyProvider.isEnable())return;
            getEconomyAPI().getEconomyProviders().put(plugin, economyProvider);
        }
    }
}