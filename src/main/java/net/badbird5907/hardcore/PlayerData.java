package net.badbird5907.hardcore;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.javatuples.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private int deaths = 0,livesLeft = 3;
    private final UUID uuid;
    private final String name;
    private boolean dead = false,toRevive = false,muted = false,ghostMuted = false;
    private Map<Integer, Pair<String,Long>> allDeaths = new HashMap<>();
    public PlayerData(UUID uuid,String name){
        this.uuid = uuid;
        this.name = name;
        this.deaths = 0;
    }
    public void death(String message){
        if (dead)
            return;
        allDeaths.put(allDeaths.size() + 1,new Pair<>(message,System.currentTimeMillis()));
        deaths++;
        livesLeft--;
        if (livesLeft <= -1)
            livesLeft = 0;
        Bukkit.broadcastMessage(ChatColor.RED + name + " has died! The now have " + livesLeft + " lives left!");
        if (livesLeft == 0) {
            Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage(ChatColor.RED + name + " has lost their final life!");
            dead = true;
        }
        save();
    }
    public void save(){
        PlayerDataManager.saveFile(Hardcore.getGson().toJson(this),new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + uuid + ".json"));
    }
    public boolean removeDeath(int index){
        if (!allDeaths.containsKey(index))
            return false;
        deaths--;
        livesLeft++;
        allDeaths.remove(index);
        Map<Integer,Pair<String,Long>> clone = new HashMap<>();
        allDeaths.forEach(clone::put);
        allDeaths.clear();
        clone.forEach((a,b)-> allDeaths.put(allDeaths.size() + 1,new Pair<>(b.getValue0(),b.getValue1())));
        return true;
    }
}
