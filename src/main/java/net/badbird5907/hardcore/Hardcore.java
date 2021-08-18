package net.badbird5907.hardcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.badbird5907.hardcore.commands.BaseCommand;
import net.badbird5907.hardcore.commands.CommandFramework;
import net.badbird5907.hardcore.commands.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hardcore extends JavaPlugin {
    @Getter
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    BaseCommand[] commands = null;
    @Getter
    private static Hardcore instance;
    @Getter
    private static CommandFramework commandFramework;
    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        commandFramework = new CommandFramework(this);
        commands = new BaseCommand[]{new DeathsCommand(),new ReviveCommand(),new RemoveDeathCommand(), new SetStatsCommand(),new ResetDataCommand()};
        getServer().getPluginManager().registerEvents(new MainListener(),this);
        Bukkit.getOnlinePlayers().forEach(player -> PlayerDataManager.join(player));
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
