package net.badbird5907.hardcore.commands;

import net.badbird5907.hardcore.Hardcore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCommand {
    public BaseCommand(){
        Hardcore.getCommandFramework().registerCommands(this);
    }
    public Hardcore plugin = Hardcore.getInstance();
    public abstract CommandResult execute(Sender sender,String[] args);
    public List<String> tabComplete(Sender sender, String[] args){
        return null;
    }
    private String usageMessage = "";

    public String getUsageMessage() {
        return usageMessage;
    }

    public void setUsageMessage(String usageMessage) {
    }
}
