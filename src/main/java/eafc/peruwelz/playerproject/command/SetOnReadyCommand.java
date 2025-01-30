package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande qui définit une action à exécuter lorsque l'objet {@link Player} est prêt.
 * Elle implémente l'interface {@link Command}.
 */
public class SetOnReadyCommand implements Command {

    /** L'appareil (Player) auquel la commande est associée. */
    private Player device;

    /** L'action à exécuter lorsque l'appareil est prêt. */
    private Runnable var;

    /**
     * Constructeur de la commande SetOnReadyCommand.
     *
     * @param device L'objet Player qui sera utilisé par la commande.
     * @param var L'action (Runnable) à exécuter lorsque l'objet Player est prêt.
     */
    public SetOnReadyCommand(Player device, Runnable var){
        this.var = var;
        this.device = device;
    }

    /**
     * Exécute la commande en définissant l'action à exécuter lorsque le Player est prêt.
     *
     * @return Toujours {@code null}, car il n'y a pas de valeur à retourner pour cette commande.
     */
    @Override
    public Object execute() {
        this.device.setOnReady(this.var);
        return null;
    }
}

