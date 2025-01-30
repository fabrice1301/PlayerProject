package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande permettant d'obtenir le statut actuel du lecteur.
 */
public class GetStatusCommand implements Command<Object> {
    private Player device;

    /**
     * Constructeur de la commande GetStatusCommand.
     * @param device Le lecteur dont on veut obtenir le statut.
     */
    public GetStatusCommand(Player device){
        this.device = device;
    }

    /**
     * Exécute la commande pour obtenir le statut actuel du lecteur.
     * @return Le statut du lecteur.
     */
    @Override
    public Object execute() {
        return device.getStatus();
    }
}
