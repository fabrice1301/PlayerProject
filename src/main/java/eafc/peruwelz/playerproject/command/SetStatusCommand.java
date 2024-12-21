package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.player.Player;

public class SetStatusCommand implements Command{

    private Player device;
    private String status;

    public SetStatusCommand(Player device,String status){
        this.status=status;
        this.device=device;
    }

    @Override
    public Object execute() {
        device.setStatus(this.status);
        return null;
    }
}
