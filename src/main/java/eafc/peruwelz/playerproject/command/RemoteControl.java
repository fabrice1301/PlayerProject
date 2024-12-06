package eafc.peruwelz.playerproject.command;

import org.springframework.stereotype.Component;


@Component
public class RemoteControl {
    private Command command;

    public RemoteControl setCommand(Command command) {
        this.command = command;
        return this;
    }

    public Object execute() {
        return command.execute();
    }
}
