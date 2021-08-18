package net.badbird5907.hardcore.commands.impl;

import net.badbird5907.hardcore.PlayerData;
import net.badbird5907.hardcore.PlayerDataManager;
import net.badbird5907.hardcore.commands.BaseCommand;
import net.badbird5907.hardcore.commands.Command;
import net.badbird5907.hardcore.commands.CommandResult;
import net.badbird5907.hardcore.commands.Sender;
import net.badbird5907.hardcore.object.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class SetStatsCommand extends BaseCommand {
    @Command(name = "setstats",permission = "hardcore.setstats")
    public CommandResult execute(Sender sender, String[] args) {
        if (args.length != 3){
            sender.sendMessage(CC.RED + "Usage: /setstats <player> <livesleft/deaths> <value>");
        }
        OfflinePlayer offlinePlayer;
        try{
            offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
        } catch (Exception e) {
            sender.sendMessage(CC.RED + "Can't find that player!");
            return CommandResult.SUCCESS;
        }
        PlayerData data = PlayerDataManager.loadData(offlinePlayer.getUniqueId());
        if (data == null) {
            sender.sendMessage(CC.RED + "Couldn't load " + offlinePlayer.getName() + "s data!");
            return CommandResult.SUCCESS;
        }
        int i;
        try{
            i = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return CommandResult.INVALID_ARGS;
        }
        if (args[1].equalsIgnoreCase("livesleft")){
            data.setLivesLeft(i);
        }else if (args[1].equalsIgnoreCase("deaths")){
            data.setDeaths(i);
        }
        return CommandResult.SUCCESS;
    }
}
