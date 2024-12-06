package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.player.Player;

public class PauseCommand implements Command{
    private Player device;
    private Object o;

    public PauseCommand(Player device){
        this.device=device;
    }


    @Override
    public Object execute() {
        device.pause();
        return null;
    }
}
