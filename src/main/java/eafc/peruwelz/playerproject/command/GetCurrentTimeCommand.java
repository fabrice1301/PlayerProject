package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande permettant d'obtenir le temps actuel d'un lecteur.
 */
public class GetCurrentTimeCommand implements Command<Object> {
    private Player device;

    /**
     * Constructeur de la commande GetCurrentTimeCommand.
     * @param device Le lecteur dont on veut obtenir le temps actuel.
     */
    public GetCurrentTimeCommand(Player device){
        this.device = device;
    }

    /**
     * Exécute la commande pour obtenir le temps actuel du lecteur en secondes.
     * @return Le temps actuel du lecteur en secondes.
     */
    @Override
    public Object execute() {
        return this.device.getCurrentTime().toSeconds();
    }
}
