package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande qui arrête un objet {@link Player}.
 * Elle implémente l'interface {@link Command}.
 */
public class StopCommand implements Command {

    /** L'appareil (Player) auquel la commande est associée. */
    private Player device;

    /**
     * Constructeur de la commande StopCommand.
     *
     * @param device L'objet Player à arrêter.
     */
    public StopCommand(Player device){
        this.device = device;
    }

    /**
     * Exécute la commande en arrêtant l'objet Player.
     *
     * @return Toujours {@code null}, car il n'y a pas de valeur à retourner pour cette commande.
     */
    @Override
    public Object execute() {
        device.stop();
        return null;
    }
}

