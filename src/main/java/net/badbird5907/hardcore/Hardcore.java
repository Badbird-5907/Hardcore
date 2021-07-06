package net.badbird5907.hardcore;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hardcore extends JavaPlugin {
    @Getter
    private static Hardcore instance;
	
    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getCommand("deaths").setExecutor(new DeathsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
