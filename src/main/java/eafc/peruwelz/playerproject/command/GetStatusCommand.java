package eafc.peruwelz.playerproject.command;


import eafc.peruwelz.playerproject.Class.StatusPlayer;
import eafc.peruwelz.playerproject.player.Player;
import org.springframework.beans.factory.annotation.Autowired;

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
