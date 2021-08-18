package net.badbird5907.hardcore;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class PlayerDataManager {
    private static Map<UUID,PlayerData> players = new ConcurrentHashMap<>();
    @SneakyThrows
    public static void join(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(Hardcore.getInstance(),()->{
            File playerData = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + player.getUniqueId() + ".json");
            PlayerData data;
            if (!playerData.exists()){
                try {
                    playerData.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data = new PlayerData(player.getUniqueId(),player.getName());
                String json = Hardcore.getGson().toJson(data);
                saveFile(json,playerData);
            }
            else {
                data = loadData(player.getUniqueId());
                if (data.isToRevive()){
                    Location loc = player.getBedSpawnLocation();
                    if (loc == null)
                        loc = Bukkit.getWorlds().get(0).getSpawnLocation();
                    player.teleport(loc);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.GREEN + "You were revived!");
                }
            }
            players.put(player.getUniqueId(),data);
        });
    }
    public static void leave(Player player){
        File file = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + player.getUniqueId() + ".json");
        PlayerData data = players.get(player.getUniqueId());
        saveFile(Hardcore.getGson().toJson(data),file);
        players.remove(player.getUniqueId());
    }
    @SneakyThrows
    public static void saveFile(String json, File file){
        System.out.println("Saving json: " + json);
        try(PrintWriter out = new PrintWriter(file)){
            out.print(json);
        }
    }
    public static PlayerData loadData(UUID uuid){
        if (players.get(uuid) != null)
            return players.get(uuid);
        File playerData;
        try{
             playerData = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + uuid + ".json");
        } catch (Exception e) {
            return null;
        }
        return Hardcore.getGson().fromJson(readLineByLineJava8(playerData.getAbsolutePath()),PlayerData.class);
    }
    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
    public static void resetData(UUID uuid){
        File file = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + uuid + ".json");
        if (Bukkit.getPlayer(uuid) != null) {
            leave(Bukkit.getPlayer(uuid));
            if (file.exists())
                file.delete();
            join(Bukkit.getPlayer(uuid));
        }
        else if (file.exists())
            file.delete();
    }
}
