package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande qui définit le volume d'un objet {@link Player}.
 * Elle implémente l'interface {@link Command}.
 */
public class SetVolumeCommand implements Command {

    /** L'appareil (Player) auquel la commande est associée. */
    private Player device;

    /** Le volume à définir pour le Player. */
    private double vol;

    /**
     * Constructeur de la commande SetVolumeCommand.
     *
     * @param device L'objet Player dont le volume sera modifié.
     * @param vol Le volume à attribuer au Player.
     */
    public SetVolumeCommand(Player device, double vol){
        this.device = device;
        this.vol = vol;
    }

    /**
     * Exécute la commande en définissant le volume de l'objet Player.
     *
     * @return Toujours {@code null}, car il n'y a pas de valeur à retourner pour cette commande.
     */
    @Override
    public Object execute() {
        device.setVolume(this.vol);
        return null;
    }
}
