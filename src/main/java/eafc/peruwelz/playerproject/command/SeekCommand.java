package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;
import javafx.util.Duration;

public class SeekCommand implements Command{
    private Player device;
    private Duration time;

    public SeekCommand(Player device,Duration time){
        this.time=time;
        this.device=device;
    }

    @Override
    public Object execute() {
        this.device.seek(this.time);
        return null;
    }
}
