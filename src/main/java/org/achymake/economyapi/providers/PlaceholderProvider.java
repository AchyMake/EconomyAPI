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
            var ecoProvider = ecoAPI.getServer().getServicesManager().getRegistration(EconomyProvider.class);
            if (ecoProvider == null) {
                return "null";
            } else {
                var eco = ecoProvider.getProvider();
                var account = eco.get(player);
                var result = eco.format(account);
                switch (params) {
                    case "account" -> {
                        return eco.currency() + result;
                    }
                    case "account_formatted" -> {
                        if (account < 1000.00) {
                            return eco.currency() + result;
                        } else if (account < 1000000.00) {
                            return eco.currency() + result.substring(0, result.length() - 4) + "K";
                        } else if (account < 1000000000.00) {
                            return eco.currency() + result.substring(0, result.length() - 8) + "M";
                        } else if (account < 1000000000000.00) {
                            return eco.currency() + result.substring(0, result.length() - 12) + "B";
                        } else if (account >= 1000000000000.00) {
                            return eco.currency() + result.substring(0, result.length() - 16) + "T";
                        }
                    }
                }
            }
        }
        return super.onPlaceholderRequest(player, params);
    }
}