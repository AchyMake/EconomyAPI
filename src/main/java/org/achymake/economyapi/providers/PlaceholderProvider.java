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
            if (ecoAPI.getRegistrationHandler().isEnable()) {
                switch (params) {
                    case "account" -> {
                        if (ecoAPI.getConfig().getBoolean("reverse")) {
                            return ecoAPI.getRegistrationHandler().getDefaultReversed(player);
                        } else {
                            return ecoAPI.getRegistrationHandler().getDefault(player);
                        }
                    }
                    case "account_formatted" -> {
                        if (ecoAPI.getConfig().getBoolean("reverse")) {
                            return ecoAPI.getRegistrationHandler().getFormattedReversed(player);
                        } else {
                            return ecoAPI.getRegistrationHandler().getFormatted(player);
                        }
                    }
                }
            } else {
                return "null";
            }
        }
        return super.onPlaceholderRequest(player, params);
    }
}