package net.badbird5907.hardcore.commands;

import net.badbird5907.hardcore.object.CC;

public enum CommandResult {
    SUCCESS(""),
    ERROR(CC.RED + "There was an error while processing that command!"),
    INVALID_ARGS(CC.RED + "Invalid Arguments!"),
    PLAYER_NOT_FOUND(CC.RED + "That player can't be found!"),
    INVALID_PLAYER(PLAYER_NOT_FOUND.getMsg()),
    ERROR_FETCHING_FROM_MOJANG(CC.RED + "Could not find that player from the Mojang API!"),
    OTHER(""),
    MOJANG_ERROR(CC.RED + "Could not contact the Mojang API! Please Try Again Later."),
    PLAYER_ONLY(CC.RED + "This is player only"),
    NO_PERMS(CC.RED + "You don't have permission to do that!");
    private String msg;
    CommandResult(String s){
        this.msg = s;
    }

    public String getMsg(String... str) {
        return msg;
    }
}
