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

public class ResetDataCommand extends BaseCommand {
    @Command(name = "resetdata",permission = "hardcore.resetdata")
    public CommandResult execute(Sender sender, String[] args) {
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
        PlayerDataManager.resetData(offlinePlayer.getUniqueId());
        sender.sendMessage(CC.GREEN + "Reset " + offlinePlayer.getName() + "'s data.");
        return CommandResult.SUCCESS;
    }
}
