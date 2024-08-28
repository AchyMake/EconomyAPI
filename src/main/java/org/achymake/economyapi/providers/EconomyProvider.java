package org.achymake.economyapi.providers;

import org.bukkit.OfflinePlayer;

public interface EconomyProvider {
    public boolean isEnable();
    /** Plugin Name
     * @return
     */
    public String getName();
    /** Your own format
     * @param value
     * @return
     */
    public String format(double value);
    /** Currency symbol such as $
     * @return
     */
    public String currency();
    /** If player has account
     * @param offlinePlayer
     */
    public boolean exists(OfflinePlayer offlinePlayer);
    /** Create player account/file
     * @param offlinePlayer
     */
    public void create(OfflinePlayer offlinePlayer);
    /** Get raw Double account
     * @param offlinePlayer
     */
    public double get(OfflinePlayer offlinePlayer);
    /** If player has
     * @param offlinePlayer
     * @param amount
     */
    public boolean has(OfflinePlayer offlinePlayer, double amount);
    /** Add Economy to Target
     * @param offlinePlayer
     * @param amount
     */
    public void add(OfflinePlayer offlinePlayer, double amount);
    /** Remove Economy from Target
     * @param offlinePlayer
     * @param amount
     */
    public void remove(OfflinePlayer offlinePlayer, double amount);
    /** If Your Plugin supports Bank function
     * @return
     */
    public boolean supportsBank();
    /** Gets raw Double Bank account
     * @param owner
     * @return
     */
    public double getBank(OfflinePlayer owner);
    /** if Target has Bank account
     * @param owner
     * @param amount
     * @return
     */
    public boolean hasBank(OfflinePlayer owner, double amount);
    /** Add Bank Economy to Target
     * @param owner
     * @param amount
     */
    public void addBank(OfflinePlayer owner, double amount);
    /** Remove Bank Economy from Target
     * @param owner
     * @param amount
     */
    public void removeBank(OfflinePlayer owner, double amount);
    /** if Target is Bank Member for Owner
     * @param owner
     * @param target
     * @return
     */
    public boolean isBankMember(OfflinePlayer owner, OfflinePlayer target);
}