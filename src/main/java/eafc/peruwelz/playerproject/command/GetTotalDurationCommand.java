package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class GetTotalDurationCommand implements Command{
    private Player device;

    public GetTotalDurationCommand(Player device){
        this.device=device;
    }
    @Override
    public Object execute() {
        return this.device.getTotalDuration().toSeconds();
    }
}
