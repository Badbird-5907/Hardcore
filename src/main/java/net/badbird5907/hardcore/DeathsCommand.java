package net.badbird5907.hardcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeathsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1){
            sender.sendMessage(ChatColor.RED + "Usage: /deaths <player>");
        }
        OfflinePlayer offlinePlayer;
        try{
            offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Can't find that player!");
            return true;
        }
        PlayerData data = PlayerDataManager.loadData(offlinePlayer.getUniqueId());
        if (data == null)
            sender.sendMessage(ChatColor.RED + "Couldn't load " + offlinePlayer.getName() + "s data!");
        sender.sendMessage(ChatColor.GREEN + offlinePlayer.getName() + " has " + ChatColor.YELLOW + data.getDeaths() + ChatColor.GREEN + " deaths. They have " + ChatColor.YELLOW + data.getLivesLeft() + ChatColor.GREEN + " lives left.");
        return true;
    }
}
