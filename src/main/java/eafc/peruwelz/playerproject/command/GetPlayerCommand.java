package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;


/**
 * Commande permettant d'obtenir une instance du lecteur.
 */
public class GetPlayerCommand implements Command<Object> {
    private Player device;

    /**
     * Constructeur de la commande GetPlayerCommand.
     * @param device Le lecteur dont on veut obtenir l'instance.
     */
    public GetPlayerCommand(Player device){
        this.device = device;
    }

    /**
     * Exécute la commande pour obtenir une instance du lecteur.
     * @return L'instance du lecteur.
     */
    @Override
    public Object execute() {
        return device.getInstance();
    }
}