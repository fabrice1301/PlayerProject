package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande qui définit le statut d'un objet {@link Player}.
 * Elle implémente l'interface {@link Command}.
 */
public class SetStatusCommand implements Command {

    /** L'appareil (Player) auquel la commande est associée. */
    private Player device;

    /** Le statut à définir pour le Player. */
    private String status;

    /**
     * Constructeur de la commande SetStatusCommand.
     *
     * @param device L'objet Player dont le statut sera modifié.
     * @param status Le statut à attribuer au Player.
     */
    public SetStatusCommand(Player device, String status){
        this.status = status;
        this.device = device;
    }

    /**
     * Exécute la commande en définissant le statut de l'objet Player.
     *
     * @return Toujours {@code null}, car il n'y a pas de valeur à retourner pour cette commande.
     */
    @Override
    public Object execute() {
        device.setStatus(this.status);
        return null;
    }
}

