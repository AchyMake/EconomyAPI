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
            var ecoProvider = ecoAPI.getEconomy();
            var eco = ecoProvider.get(player);
            var currency = ecoProvider.currency();
            var result = currency + ecoProvider.format(eco);
            switch (params) {
                case "account" -> {
                    return result;
                }
            }
        }
        return super.onPlaceholderRequest(player, params);
    }
}
