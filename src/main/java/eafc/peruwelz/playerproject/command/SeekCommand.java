package eafc.peruwelz.playerproject.command;

import eafc.peruwelz.playerproject.player.Player;
import javafx.util.Duration;

/**
 * Commande pour déplacer la lecture d'un appareil (Player) à un moment spécifique.
 * Cette commande permet de "chercher" une position donnée dans la lecture en cours.
 */
public class SeekCommand implements Command {

    /** L'appareil (Player) sur lequel la commande est exécutée */
    private Player device;

    /** Le moment (Duration) vers lequel déplacer la lecture */
    private Duration time;

    /**
     * Constructeur de la commande SeekCommand.
     *
     * @param device L'appareil (Player) sur lequel la commande sera exécutée.
     * @param time La position (en durée) vers laquelle déplacer la lecture.
     */
    public SeekCommand(Player device, Duration time) {
        this.time = time;
        this.device = device;
    }

    /**
     * Exécute la commande pour déplacer la lecture à la position spécifiée.
     *
     * @return null, car aucune donnée n'est retournée après l'exécution.
     */
    @Override
    public Object execute() {
        // Déplace la lecture à la position spécifiée
        this.device.seek(this.time);
        return null; // Aucun résultat à renvoyer
    }
}
