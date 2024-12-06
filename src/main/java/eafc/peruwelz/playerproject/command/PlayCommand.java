package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.player.Player;

public class PlayCommand implements Command{
    private Player device;

    public PlayCommand(Player device){
        this.device=device;

    }


    @Override
    public Object execute() {
        device.play();
        return null;
    }

}
