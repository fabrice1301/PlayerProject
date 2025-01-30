package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour définir un comportement à exécuter à la fin de la lecture d'un média.
 * Cette commande permet de spécifier une action à exécuter lorsque la lecture d'un média se termine.
 */
public class SetOnEndOfMediaCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /** L'action à exécuter lorsque la lecture du média est terminée */
    private Runnable var;

    /**
     * Constructeur de la commande SetOnEndOfMediaCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     * @param var L'action (Runnable) à exécuter à la fin de la lecture du média.
     */
    public SetOnEndOfMediaCommand(Player device, Runnable var) {
        this.var = var;
        this.device = device;
    }

    /**
     * Exécute la commande pour définir l'action à exécuter à la fin de la lecture du média.
     *
     * @return null, car aucune donnée n'est retournée après l'exécution.
     */
    @Override
    public Object execute() {
        // Définit l'action à exécuter à la fin de la lecture du média
        this.device.setOnEndOfMedia(this.var);
        return null; // Aucun résultat à renvoyer
    }
}

