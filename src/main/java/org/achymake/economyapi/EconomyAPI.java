package org.achymake.economyapi;

import org.achymake.economyapi.providers.EconomyProvider;
import org.achymake.economyapi.providers.PlaceholderProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class EconomyAPI extends JavaPlugin {
    private static EconomyAPI instance;
    private static EconomyProvider economy = null;
    @Override
    public void onEnable() {
        instance = this;
        var reg = getServer().getServicesManager().getRegistration(EconomyProvider.class);
        if (reg != null) {
            economy = reg.getProvider();
        }
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderProvider().register();
        }
        getLogger().log(Level.INFO, "Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
    }
    @Override
    public void onDisable() {
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderProvider().unregister();
        }
    }
    public EconomyProvider getEconomy() {
        return economy;
    }
    public static EconomyAPI getInstance() {
        return instance;
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
    public boolean isSpigot() {
        return getMinecraftProvider().equals("CraftBukkit");
    }
}