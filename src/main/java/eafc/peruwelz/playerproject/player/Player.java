package eafc.peruwelz.playerproject.player;

import javafx.util.Duration;
import org.springframework.stereotype.Component;
/**
 * Interface représentant un lecteur multimédia.
 * Cette interface fournit des méthodes pour charger, lire, contrôler et obtenir des informations
 * sur les médias, ainsi que pour gérer l'état du lecteur et les événements associés.
 */
@Component
public interface Player {

    /**
     * Retourne l'instance du lecteur multimédia.
     *
     * @return l'instance du lecteur, généralement un objet de type MediaPlayer.
     */
    Object getInstance();

    /**
     * Charge une piste audio ou vidéo depuis le chemin spécifié.
     *
     * @param path le chemin du fichier à charger.
     */
    void loadTrack(String path);

    /**
     * Démarre la lecture du média chargé.
     */
    void play();

    /**
     * Arrête la lecture du média.
     */
    void stop();

    /**
     * Met la lecture du média en pause.
     */
    void pause();

    /**
     * Vérifie si le lecteur multimédia est nul.
     *
     * @return true si le lecteur est nul, sinon false.
     */
    boolean isNull();

    /**
     * Retourne le statut actuel du lecteur multimédia.
     *
     * @return un objet représentant le statut du lecteur (par exemple, lecture, pause, etc.).
     */
    Object getStatus();

    /**
     * Définit le statut du lecteur multimédia.
     *
     * @param status le statut à définir pour le lecteur (exemple : "playing", "paused").
     */
    void setStatus(String status);

    /**
     * Définit le volume du lecteur multimédia.
     *
     * @param volume le volume à définir, valeur entre 0.0 (silence) et 1.0 (volume maximum).
     */
    void setVolume(double volume);

    /**
     * Retourne le volume actuel du lecteur multimédia.
     *
     * @return le volume actuel du lecteur.
     */
    double getVolume();

    /**
     * Retourne le temps actuel de lecture du média.
     *
     * @return la durée représentant le temps actuel de lecture.
     */
    Duration getCurrentTime();

    /**
     * Retourne la durée totale du média.
     *
     * @return la durée totale du média.
     */
    Duration getTotalDuration();

    /**
     * Définit une action à exécuter lorsque le lecteur multimédia est prêt à être utilisé.
     *
     * @param var la fonction à exécuter lorsque le lecteur est prêt.
     */
    void setOnReady(Runnable var);

    /**
     * Définit une action à exécuter lorsque le lecteur multimédia a terminé de lire le média.
     *
     * @param var la fonction à exécuter lorsque la lecture du média est terminée.
     */
    void setOnEndOfMedia(Runnable var);

    /**
     * Déplace la lecture à un moment spécifique dans le média.
     *
     * @param time la durée à laquelle se rendre dans le média.
     */
    void seek(Duration time);
}
