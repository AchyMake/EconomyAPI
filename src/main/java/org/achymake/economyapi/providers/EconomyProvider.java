package org.achymake.economyapi.providers;

import org.bukkit.OfflinePlayer;

public interface EconomyProvider {
    public boolean isEnable();
    public String getName();
    public String format(double value);
    public String currency();
    public boolean exists(OfflinePlayer offlinePlayer);
    public void create(OfflinePlayer offlinePlayer);
    public double get(OfflinePlayer offlinePlayer);
    public boolean has(OfflinePlayer offlinePlayer, double amount);
    public void add(OfflinePlayer offlinePlayer, double amount);
    public void remove(OfflinePlayer offlinePlayer, double amount);
    public boolean supportsBank();
    public double getBank(OfflinePlayer offlinePlayer);
    public boolean hasBank(OfflinePlayer offlinePlayer);
    public void addBank(OfflinePlayer offlinePlayer, double amount);
    public void removeBank(OfflinePlayer offlinePlayer, double amount);
    public boolean isBankMember(OfflinePlayer owner, OfflinePlayer target);
}