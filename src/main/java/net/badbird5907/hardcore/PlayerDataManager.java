package net.badbird5907.hardcore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class PlayerDataManager {
    private static final Map<UUID,PlayerData> dataMap = new ConcurrentHashMap<>();
    @SneakyThrows
    public static void join(Player player){
        File playerData = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + player.getUniqueId() + ".json");
        if (!playerData.exists()){
            playerData.createNewFile();
            PlayerData data = new PlayerData(player.getUniqueId(),player.getName());
            dataMap.put(player.getUniqueId(),data);
            String json = new Gson().toJson(data);
            saveFile(json,playerData);
        }
        else dataMap.put(player.getUniqueId(),loadData(player.getUniqueId()));
    }
    public static void leave(Player player){
        File playerData = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + player.getUniqueId() + ".json");
        PlayerData data = dataMap.get(player.getUniqueId());
        saveFile(new Gson().toJson(data),playerData);
    }
    @SneakyThrows
    public static void saveFile(String json, File file){
        System.out.println("Saving json: " + json);
        try(PrintWriter out = new PrintWriter(file)){
            out.print(json);
        }
    }
    public static PlayerData loadData(UUID uuid){
        File playerData = new File(Hardcore.getInstance().getDataFolder().getAbsolutePath() + "/" + uuid + ".json");
        return new Gson().fromJson(readLineByLineJava8(playerData.getAbsolutePath()),PlayerData.class);
    }
    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}
