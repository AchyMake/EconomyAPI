package org.achymake.economyapi.handlers;

import org.achymake.economyapi.EconomyAPI;
import org.bukkit.scheduler.BukkitScheduler;

public class ScheduleHandler {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    private BukkitScheduler getScheduler() {
        return getEconomyAPI().getServer().getScheduler();
    }
    public int runLater(Runnable runnable, long timer) {
        return getScheduler().runTaskLater(getEconomyAPI(), runnable, timer).getTaskId();
    }
    public int runAsynchronously(Runnable runnable) {
        return getScheduler().runTaskAsynchronously(getEconomyAPI(), runnable).getTaskId();
    }
    public boolean isQueued(int taskID) {
        return getScheduler().isQueued(taskID);
    }
    public void cancel(int taskID) {
        getScheduler().cancelTask(taskID);
    }
    public void cancelAll() {
        getScheduler().cancelTasks(getEconomyAPI());
    }
}