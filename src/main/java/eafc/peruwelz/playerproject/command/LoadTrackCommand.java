package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.Class.AlertClass;
import eafc.peruwelz.playerproject.player.Player;

/**
 * Commande pour charger une piste audio dans un appareil (Player).
 * Cette commande charge une piste spécifiée par le chemin dans l'appareil.
 */
public class LoadTrackCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /** Le chemin de la piste à charger */
    private String path;

    /**
     * Constructeur de la commande LoadTrackCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     * @param path Le chemin de la piste à charger.
     */
    public LoadTrackCommand(Player device, String path) {
        this.path = path;
        this.device = device;
    }

    /**
     * Exécute la commande pour charger la piste spécifiée dans l'appareil.
     * Si une erreur survient lors du chargement de la piste, une alerte est déclenchée.
     *
     * @return null, car aucune donnée n'est retournée après l'exécution.
     */
    @Override
    public Object execute() {
        try {
            this.device.loadTrack(this.path); // Charge la piste dans l'appareil
        } catch (Exception e) {
            // Si une erreur se produit, une alerte est déclenchée
            AlertClass.playingError();
        }
        return null; // Aucun résultat à renvoyer
    }
}

