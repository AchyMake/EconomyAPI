package org.achymake.economyapi;

import org.achymake.economyapi.commands.EconomyAPICommand;
import org.achymake.economyapi.handlers.RegistrationHandler;
import org.achymake.economyapi.handlers.ScheduleHandler;
import org.achymake.economyapi.listeners.PlayerJoin;
import org.achymake.economyapi.listeners.ServiceRegister;
import org.achymake.economyapi.providers.EconomyProvider;
import org.achymake.economyapi.providers.PlaceholderProvider;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class EconomyAPI extends JavaPlugin {
    private static EconomyAPI instance;
    private static Message message;
    private static ScheduleHandler scheduleHandler;
    private static UpdateChecker updateChecker;
    private static RegistrationHandler registrationHandler;
    private final Map<Plugin, EconomyProvider> rsp = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        scheduleHandler = new ScheduleHandler();
        updateChecker = new UpdateChecker();
        registrationHandler = new RegistrationHandler();
        new EconomyAPICommand();
        new PlayerJoin();
        new ServiceRegister();
        reload();
        new PlaceholderProvider().register();
        getLogger().log(Level.INFO, "Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
    }
    @Override
    public void onDisable() {
        new PlaceholderProvider().unregister();
        getScheduleHandler().cancelAll();
        if (!getEconomyProviders().isEmpty()) {
            getEconomyProviders().clear();
        }
    }
    public void reload() {
        var file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            try {
                getConfig().save(file);
            } catch (IOException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        }
    }
    public Plugin getPlugin(String pluginName) {
        if (getManager().isPluginEnabled(pluginName)) {
            return getManager().getPlugin(pluginName);
        } else {
            return null;
        }
    }
    public PluginManager getManager() {
        return getServer().getPluginManager();
    }
    public Map<Plugin, EconomyProvider> getEconomyProviders() {
        return rsp;
    }
    public RegistrationHandler getRegistrationHandler() {
        return registrationHandler;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public Message getMessage() {
        return message;
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
}