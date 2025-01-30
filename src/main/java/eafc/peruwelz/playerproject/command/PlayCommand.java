package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour lire une piste sur un appareil (Player).
 * Cette commande démarre la lecture de l'appareil lorsqu'elle est exécutée.
 */
public class PlayCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /**
     * Constructeur de la commande PlayCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     */
    public PlayCommand(Player device) {
        this.device = device;
    }

    /**
     * Exécute la commande pour démarrer la lecture de l'appareil.
     *
     * @return null, car aucune donnée n'est retournée après l'exécution.
     */
    @Override
    public Object execute() {
        // Démarre la lecture de l'appareil
        device.play();
        return null; // Aucun résultat à renvoyer
    }
}

