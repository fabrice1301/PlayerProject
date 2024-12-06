package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.player.Player;

public class StopCommand implements Command{
    private Player device;

    public StopCommand(Player device){
        this.device=device;
    }

    @Override
    public Object execute() {
        device.stop();
        return null;
    }


}
