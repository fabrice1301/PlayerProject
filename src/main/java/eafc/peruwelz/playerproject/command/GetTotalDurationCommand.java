package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour obtenir la durée totale d'un appareil (Player).
 * Cette commande permet de récupérer la durée totale en secondes.
 */
public class GetTotalDurationCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /**
     * Constructeur de la commande GetTotalDurationCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     */
    public GetTotalDurationCommand(Player device) {
        this.device = device;
    }

    /**
     * Exécute la commande et retourne la durée totale de l'appareil en secondes.
     *
     * @return La durée totale de l'appareil en secondes.
     */
    @Override
    public Object execute() {
        return this.device.getTotalDuration().toSeconds();
    }
}

