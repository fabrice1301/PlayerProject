package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

public class SetOnReadyCommand implements Command{
    private Player device;
    private Runnable var;

    public SetOnReadyCommand(Player device,Runnable var){
        this.var=var;
        this.device=device;
    }
    @Override
    public Object execute() {
        this.device.setOnReady(this.var);
        return null;
    }
}
