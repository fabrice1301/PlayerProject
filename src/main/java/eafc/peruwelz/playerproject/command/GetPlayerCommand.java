package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class GetPlayerCommand implements Command{
    private Player device;

    public GetPlayerCommand(Player device){
        this.device=device;
    }


    @Override
    public Object execute() {
        return device.getInstance();
    }
}
