package org.achymake.economyapi.providers;

import org.bukkit.OfflinePlayer;

public interface EconomyProvider {
    public boolean isEnable();
    public String getName();
    public String format(double value);
    public String currency();
    public boolean exists(OfflinePlayer offlinePlayer);
    public boolean create(OfflinePlayer offlinePlayer);
    public double get(OfflinePlayer offlinePlayer);
    public boolean has(OfflinePlayer offlinePlayer, double amount);
    public boolean add(OfflinePlayer offlinePlayer, double amount);
    public boolean remove(OfflinePlayer offlinePlayer, double amount);
}