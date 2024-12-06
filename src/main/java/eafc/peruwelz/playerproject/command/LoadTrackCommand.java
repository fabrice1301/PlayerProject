package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class LoadTrackCommand implements Command{
    private Player device;
    private String path;

    public LoadTrackCommand(Player device,String path){

        this.path=path;
        this.device=device;
    }
    @Override
    public Object execute() {
        this.device.loadTrack(this.path);
        return null;
    }
}
