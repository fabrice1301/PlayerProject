package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour mettre en pause un appareil (Player).
 * Cette commande met l'appareil en pause lorsqu'elle est exécutée.
 */
public class PauseCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /** Un objet supplémentaire, actuellement non utilisé dans cette commande */
    private Object o;

    /**
     * Constructeur de la commande PauseCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     */
    public PauseCommand(Player device) {
        this.device = device;
    }

    /**
     * Exécute la commande pour mettre en pause l'appareil.
     *
     * @return null, car aucune donnée n'est retournée après l'exécution.
     */
    @Override
    public Object execute() {
        // Met en pause l'appareil
        device.pause();
        return null; // Aucun résultat à renvoyer
    }
}

