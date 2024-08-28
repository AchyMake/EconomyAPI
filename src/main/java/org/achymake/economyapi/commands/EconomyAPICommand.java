package org.achymake.economyapi.commands;

import org.achymake.economyapi.EconomyAPI;
import org.achymake.economyapi.Message;
import org.achymake.economyapi.providers.EconomyProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EconomyAPICommand implements CommandExecutor, TabCompleter {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    private Message getMessage() {
        return getEconomyAPI().getMessage();
    }
    public EconomyAPICommand() {
        getEconomyAPI().getCommand("economyapi").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            var ecoAPI = getEconomyAPI();
            var ecoProvider = ecoAPI.getServer().getServicesManager().getRegistration(EconomyProvider.class);
            getMessage().send(sender, getEconomyAPI().name() + " " + getEconomyAPI().version());
            if (ecoProvider != null) {
                var eco = ecoProvider.getProvider();
                getMessage().send(sender, "Economy: " + eco.getName());
            } else {
                getMessage().send(sender, "Economy: null");
            }
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                getEconomyAPI().reload();
                getMessage().send(sender, "EconomyAPI: reloaded");
                return true;
            } else {
                return false;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("convert")) {
                var eco1 = args[1];
                var eco2 = args[2];
                Collection<RegisteredServiceProvider<EconomyProvider>> economies = getEconomyAPI().getServer().getServicesManager().getRegistrations(EconomyProvider.class);
                if (economies.size() < 2) {
                    getMessage().send(sender, "could not find more economy providers");
                } else {
                    EconomyProvider provider1 = null;
                    EconomyProvider provider2 = null;
                    for (var econ : economies) {
                        var names = econ.getProvider().getName();
                        if (names.equalsIgnoreCase(eco1)) {
                            provider1 = econ.getProvider();
                        }
                        if (names.equalsIgnoreCase(eco2)) {
                            provider2 = econ.getProvider();
                        }
                    }
                    if (provider1 != null || provider2 != null) {
                        getMessage().send(sender, "Trying to convert");
                        getMessage().send(sender, "it might lag depending how many players has joined");
                        for (OfflinePlayer offlinePlayer : getEconomyAPI().getServer().getOfflinePlayers()) {
                            if (provider1.exists(offlinePlayer)) {
                                if (provider2.exists(offlinePlayer)) {
                                    provider2.create(offlinePlayer);
                                    var result = provider1.get(offlinePlayer) - provider2.get(offlinePlayer);
                                    if (result > 0) {
                                        provider2.add(offlinePlayer, result);
                                    } else if (result < 0) {
                                        provider2.remove(offlinePlayer, -result);
                                    }
                                }
                            }
                        }
                        getMessage().send(sender, "Economy has been converted");
                        getMessage().send(sender, "before uninstall the old one please check to be sure");
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var commands = new ArrayList<String>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                commands.add("reload");
                commands.add("convert");
            }
        }
        return commands;
    }
}