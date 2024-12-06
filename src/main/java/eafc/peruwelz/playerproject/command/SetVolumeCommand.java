package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class SetVolumeCommand implements Command{

    private Player device;
    private double vol;

    public SetVolumeCommand(Player device, double vol){
        this.device=device;
        this.vol=vol;
    }

    @Override
    public Object execute() {
        device.setVolume(this.vol);
        return null;
    }


}
