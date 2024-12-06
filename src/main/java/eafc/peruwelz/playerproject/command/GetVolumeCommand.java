package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class GetVolumeCommand implements Command{
    private Player device;

    public GetVolumeCommand(Player device){
        this.device=device;
    }
    @Override
    public Object execute() {
        return this.device.getVolume();
    }
}
