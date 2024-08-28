package org.achymake.economyapi.commands;

import org.achymake.economyapi.EconomyAPI;
import org.achymake.economyapi.Message;
import org.achymake.economyapi.providers.EconomyProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
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
            getMessage().send(sender, getEconomyAPI().name() + ": " + getEconomyAPI().version());
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
                var plugin1 = getEconomyAPI().getPlugin(args[1]);
                var plugin2 = getEconomyAPI().getPlugin(args[2]);
                if (getEconomyAPI().getEconomyProviders().size() < 2) {
                    getMessage().send(sender, "could not find more economy providers");
                } else {
                    if (plugin1 != null || plugin2 != null) {
                        var provider1 = getEconomyAPI().getEconomyProviders().get(plugin1);
                        var provider2 = getEconomyAPI().getEconomyProviders().get(plugin2);
                        if (provider1 == provider2) {
                            getMessage().send(sender, "Economy can not convert to same provider");
                        } else {
                            getMessage().send(sender, "Trying to convert");
                            getMessage().send(sender, "it might lag depending how many players has joined");
                            for (OfflinePlayer offlinePlayer : getEconomyAPI().getServer().getOfflinePlayers()) {
                                if (provider1.exists(offlinePlayer)) {
                                    provider2.create(offlinePlayer);
                                    var result = provider1.get(offlinePlayer) - provider2.get(offlinePlayer);
                                    if (result > 0) {
                                        provider2.add(offlinePlayer, result);
                                    } else if (result < 0) {
                                        provider2.remove(offlinePlayer, -result);
                                    }
                                    if (provider1.supportsBank() && provider2.supportsBank()) {
                                        var bankResult = provider1.getBank(offlinePlayer) - provider2.getBank(offlinePlayer);
                                        if (result > 0) {
                                            provider2.addBank(offlinePlayer, bankResult);
                                        } else if (result < 0) {
                                            provider2.removeBank(offlinePlayer, -bankResult);
                                        }
                                    }
                                }
                            }
                            getMessage().send(sender, "Economy has been converted");
                            getMessage().send(sender, "before uninstall the old one please check to be sure");
                        }
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
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("convert")) {
                    for (Plugin plugin : getEconomyAPI().getEconomyProviders().keySet()) {
                        commands.add(plugin.getName());
                    }
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("convert")) {
                    for (Plugin plugin : getEconomyAPI().getEconomyProviders().keySet()) {
                        if (!plugin.getName().equalsIgnoreCase(args[1])) {
                            commands.add(plugin.getName());
                        }
                    }
                }
            }
        }
        return commands;
    }
}