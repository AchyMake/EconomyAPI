package org.achymake.economyapi.handlers;

import org.achymake.economyapi.EconomyAPI;
import org.achymake.economyapi.providers.EconomyProvider;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.text.DecimalFormat;

public class RegistrationHandler {
    public RegisteredServiceProvider<EconomyProvider> getRegistration() {
        return EconomyAPI.getInstance().getServer().getServicesManager().getRegistration(EconomyProvider.class);
    }
    public boolean isEnable() {
        if (getRegistration() != null) {
            return getRegistration().getProvider().isEnable();
        } else {
            return false;
        }
    }
    public String getDefault(Player player) {
        if (isEnable()) {
            var eco = getRegistration().getProvider();
            var account = eco.get(player);
            var result = eco.format(account);
            return eco.currency() + result;
        } else {
            return "";
        }
    }
    public String getDefaultReversed(Player player) {
        if (isEnable()) {
            var eco = getRegistration().getProvider();
            var account = eco.get(player);
            var result = eco.format(account);
            return result + eco.currency();
        } else {
            return "";
        }
    }
    public String getFormatted(Player player) {
        if (isEnable()) {
            var eco = getRegistration().getProvider();
            var account = eco.get(player);
            var formatted = new DecimalFormat("#,##0").format(account);
            if (account < 1000.00) {
                return eco.currency() + formatted;
            } else if (account < 1000000.00) {
                return eco.currency() + formatted.substring(0, formatted.length() - 4) + "K";
            } else if (account < 1000000000.00) {
                return eco.currency() + formatted.substring(0, formatted.length() - 8) + "M";
            } else if (account < 1000000000000.00) {
                return eco.currency() + formatted.substring(0, formatted.length() - 12) + "B";
            } else if (account >= 1000000000000.00) {
                return eco.currency() + formatted.substring(0, formatted.length() - 16) + "T";
            } else {
                return getDefault(player);
            }
        } else {
            return "";
        }
    }
    public String getFormattedReversed(Player player) {
        if (isEnable()) {
            var eco = getRegistration().getProvider();
            var account = eco.get(player);
            var formatted = new DecimalFormat("#,##0").format(account);
            if (account < 1000.00) {
                return formatted + eco.currency();
            } else if (account < 1000000.00) {
                return "K" + formatted.substring(0, formatted.length() - 4) + eco.currency();
            } else if (account < 1000000000.00) {
                return "M" + formatted.substring(0, formatted.length() - 8) + eco.currency();
            } else if (account < 1000000000000.00) {
                return "B" + formatted.substring(0, formatted.length() - 12) + eco.currency();
            } else if (account >= 1000000000000.00) {
                return "T" + formatted.substring(0, formatted.length() - 16) + eco.currency();
            } else {
                return getDefaultReversed(player);
            }
        } else {
            return "";
        }
    }
    public void convert(Plugin from, Plugin to) {

    }
}
