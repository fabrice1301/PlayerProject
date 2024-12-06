package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.player.Player;

public class GetStatusCommand implements Command{
    private Player device;

    public GetStatusCommand(Player device){
        this.device=device;
    }


    @Override
    public Object execute() {
        return device.getStatus();
    }
}
