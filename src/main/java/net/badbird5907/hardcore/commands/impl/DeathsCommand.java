package net.badbird5907.hardcore.commands.impl;

import net.badbird5907.hardcore.PlayerData;
import net.badbird5907.hardcore.PlayerDataManager;
import net.badbird5907.hardcore.commands.BaseCommand;
import net.badbird5907.hardcore.commands.Command;
import net.badbird5907.hardcore.commands.CommandResult;
import net.badbird5907.hardcore.commands.Sender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DeathsCommand extends BaseCommand {
    public String convertTime(long time){
        Date time1 = new Date(time);
        DateFormat EXPIRE_FORMAT = new SimpleDateFormat("MMM dd, h:mm a z");
        EXPIRE_FORMAT.setTimeZone(TimeZone.getTimeZone("America/New_York")); // or whatever relevant TimeZone id
        return EXPIRE_FORMAT.format(time1);
    }

    @Command(name = "deaths",aliases = {"lives"})
    public CommandResult execute(Sender sender, String[] args) {
        if (args.length != 1){
            sender.sendMessage(ChatColor.RED + "Usage: /deaths <player>");
        }
        OfflinePlayer offlinePlayer;
        try{
            offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Can't find that player!");
            return CommandResult.SUCCESS;
        }
        PlayerData data = PlayerDataManager.loadData(offlinePlayer.getUniqueId());
        if (data == null) {
            sender.sendMessage(ChatColor.RED + "Couldn't load " + offlinePlayer.getName() + "s data!");
            return CommandResult.SUCCESS;
        }
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------");
        sender.sendMessage(ChatColor.GREEN + offlinePlayer.getName() + " has " + ChatColor.YELLOW + data.getDeaths() + ChatColor.GREEN + " deaths. They have " + ChatColor.YELLOW + data.getLivesLeft() + ChatColor.GREEN + " lives left.");
        if (data.getAllDeaths() != null){
            if (data.getAllDeaths().size() != 0){
                sender.sendMessage("");
                sender.sendMessage(ChatColor.GOLD + "Death # | Message | Death Time (EST)");
                data.getAllDeaths().forEach((i,d)-> sender.sendMessage(ChatColor.GOLD + "#" + i + ": " + ChatColor.GREEN + d.getValue0() + " | " + convertTime(d.getValue1())));
                sender.sendMessage("");
            }
        }
        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------");
        return CommandResult.SUCCESS;
    }
}
