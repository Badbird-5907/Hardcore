package net.badbird5907.hardcore;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.*;

public class DeathsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) sender.sendMessage(RED + "Usage: /deaths <player>");
        OfflinePlayer offlinePlayer;
        try {
            offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        } catch (Exception e) {
            sender.sendMessage(RED + "Can't find that player!");
            return true;
        }
        PlayerData data = PlayerDataManager.loadData(offlinePlayer.getUniqueId());
        if (data == null) sender.sendMessage(RED + "Couldn't load " + offlinePlayer.getName() + "s data!");
        sender.sendMessage(GREEN + offlinePlayer.getName() + " has " + YELLOW + data.getDeaths() + GREEN + " deaths. They have " + YELLOW + data.getLivesLeft() + GREEN + " lives left.");
        return true;
    }
}
