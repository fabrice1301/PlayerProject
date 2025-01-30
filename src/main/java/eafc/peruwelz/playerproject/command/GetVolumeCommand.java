package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour obtenir le volume actuel d'un appareil (Player).
 * Cette commande permet de récupérer le niveau de volume de l'appareil.
 */
public class GetVolumeCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /**
     * Constructeur de la commande GetVolumeCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     */
    public GetVolumeCommand(Player device) {
        this.device = device;
    }

    /**
     * Exécute la commande et retourne le volume actuel de l'appareil.
     *
     * @return Le volume actuel de l'appareil.
     */
    @Override
    public Object execute() {
        return this.device.getVolume();
    }
}

