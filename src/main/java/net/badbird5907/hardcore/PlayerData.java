package net.badbird5907.hardcore;

import com.google.gson.Gson;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;

import java.io.File;
import java.util.UUID;

@Getter
public class PlayerData {
    private int deaths, livesLeft = 3;
    private final UUID uuid;
    private final String name;
    private boolean dead = false;

    public PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.deaths = 0;
    }

    public void death() {
        if (dead) return;
        deaths++;
        livesLeft--;
        Bukkit.broadcastMessage(ChatColor.RED + name + " has died! The now have " + livesLeft + " lives left!");
        if (deaths >= 3) {
            Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage(ChatColor.RED + name + " has lost their final life!");
            dead = true;
        }
        save();
    }

    public void save() {
        PlayerDataManager.saveFile(new Gson().toJson(this), new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + uuid + ".json"));
    }
}
