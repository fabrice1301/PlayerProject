package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class SetOnEndOfMediaCommand implements Command{
    private Player device;
    private Runnable var;

    public SetOnEndOfMediaCommand(Player device, Runnable var){
        this.var=var;
        this.device=device;
    }
    @Override
    public Object execute() {
        this.device.setOnEndOfMedia(this.var);
        return null;
    }
}
