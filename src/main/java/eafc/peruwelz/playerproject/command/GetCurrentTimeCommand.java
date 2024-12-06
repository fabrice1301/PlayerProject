package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class GetCurrentTimeCommand implements Command{
    private Player device;

    public GetCurrentTimeCommand(Player device){
        this.device=device;
    }

    @Override
    public Object execute() {
        return this.device.getCurrentTime().toSeconds();
    }
}
