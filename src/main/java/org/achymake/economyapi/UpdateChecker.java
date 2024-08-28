package org.achymake.economyapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;

public class UpdateChecker {
    private EconomyAPI getEconomyAPI() {
        return EconomyAPI.getInstance();
    }
    private FileConfiguration getConfig() {
        return getEconomyAPI().getConfig();
    }
    private Message getMessage() {
        return getEconomyAPI().getMessage();
    }
    public void getUpdate(Player player) {
        if (getConfig().getBoolean("notify-update")) {
            if (player.hasPermission("economyapi.event.join.update")) {
                getEconomyAPI().getScheduleHandler().runLater(new Runnable() {
                    @Override
                    public void run() {
                        getLatest((latest) -> {
                            if (!getEconomyAPI().version().equals(latest)) {
                                getMessage().send(player, getEconomyAPI().name() + "&6 has new update:");
                                getMessage().send(player, "-&a https://www.spigotmc.org/resources/119211/");
                            }
                        });
                    }
                }, 8);
            }
        }
    }
    public void getUpdate() {
        if (getConfig().getBoolean("notify-update")) {
            getEconomyAPI().getScheduleHandler().runAsynchronously(new Runnable() {
                @Override
                public void run() {
                    getLatest((latest) -> {
                        if (!getEconomyAPI().version().equals(latest)) {
                            getMessage().sendLog(Level.INFO, getEconomyAPI().name() + " has new update:");
                            getMessage().sendLog(Level.INFO, "- https://www.spigotmc.org/resources/119211/");
                        }
                    });
                }
            });
        }
    }
    public void getLatest(Consumer<String> consumer) {
        try (var inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 119211).openStream()) {
            var scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
                scanner.close();
            } else {
                inputStream.close();
            }
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
}