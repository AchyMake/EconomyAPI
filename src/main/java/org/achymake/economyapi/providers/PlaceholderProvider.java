package org.achymake.economyapi.providers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.achymake.economyapi.EconomyAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlaceholderProvider extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "economy";
    }
    @Override
    public String getAuthor() {
        return "AchyMake";
    }
    @Override
    public String getVersion() {
        return EconomyAPI.getInstance().version();
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        } else {
            var ecoAPI = EconomyAPI.getInstance();
            var eco = ecoAPI.getServer().getServicesManager().getRegistration(EconomyProvider.class).getProvider();
            var account = eco.get(player);
            var result = eco.currency() + eco.format(account);
            String accountString = eco.format(account);
            switch (params) {
                case "account" -> {
                    return result;
                }
                case "account_formatted" -> {
                    if (account < 1000.00) {
                        return result;
                    } else if (account < 1000000.00) {
                        var resultFormatted = accountString.substring(0, accountString.length() - 4);
                        return eco.currency() + resultFormatted + "K";
                    } else if (account >= 1000000.00) {
                        var resultFormatted = accountString.substring(0, accountString.length() - 8);
                        return eco.currency() + resultFormatted + "M";
                    }
                }
            }
        }
        return super.onPlaceholderRequest(player, params);
    }
}