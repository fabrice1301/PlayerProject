package eafc.peruwelz.playerproject.player;


import eafc.peruwelz.playerproject.Class.StatusPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;


/**
 * La classe MPlayer implémente l'interface Player et fournit des méthodes
 * pour gérer la lecture de médias via la bibliothèque JavaFX.
 * Elle utilise un objet MediaPlayer pour charger, lire, arrêter et manipuler des médias.
 */
public class MPlayer implements Player {
    private MediaPlayer mediaPlayer;

    /**
     * Retourne l'instance du MediaPlayer.
     *
     * @return l'instance du MediaPlayer utilisée pour la lecture des médias.
     */
    @Override
    public Object getInstance() {
        return this.mediaPlayer;
    }

    /**
     * Charge un fichier audio ou vidéo à partir d'un chemin spécifié.
     *
     * @param path le chemin du fichier à charger.
     */
    @Override
    public void loadTrack(String path) {
        File file = new File(path);
        Media media = new Media(file.toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
    }

    /**
     * Démarre la lecture du média chargé.
     */
    @Override
    public void play() {
        this.mediaPlayer.play();
    }

    /**
     * Arrête la lecture du média.
     */
    @Override
    public void stop() {
        this.mediaPlayer.stop();
    }

    /**
     * Met la lecture du média en pause.
     */
    @Override
    public void pause() {
        this.mediaPlayer.pause();
    }

    /**
     * Vérifie si le MediaPlayer est nul.
     *
     * @return true si le MediaPlayer est nul, sinon false.
     */
    @Override
    public boolean isNull() {
        return mediaPlayer == null;
    }

    /**
     * Retourne le statut actuel du lecteur.
     *
     * @return le statut actuel sous forme d'objet.
     */
    @Override
    public Object getStatus() {
        return StatusPlayer.getIntance().getStatus();
    }

    /**
     * Définit le statut du lecteur.
     *
     * @param status le nouveau statut du lecteur sous forme de chaîne.
     */
    @Override
    public void setStatus(String status) {
        StatusPlayer.getIntance().setStatus(status);
    }

    /**
     * Définit le volume du lecteur multimédia.
     *
     * @param volume le volume à définir (valeur entre 0.0 et 1.0).
     */
    @Override
    public void setVolume(double volume) {
        this.mediaPlayer.setVolume(volume);
    }

    /**
     * Retourne le volume actuel du lecteur multimédia.
     *
     * @return le volume actuel du lecteur.
     */
    @Override
    public double getVolume() {
        return this.mediaPlayer.getVolume();
    }

    /**
     * Retourne le temps actuel de la lecture du média.
     *
     * @return la durée du temps actuel de lecture.
     */
    @Override
    public Duration getCurrentTime() {
        return this.mediaPlayer.getCurrentTime();
    }

    /**
     * Retourne la durée totale du média.
     *
     * @return la durée totale du média.
     */
    @Override
    public Duration getTotalDuration() {
        return this.mediaPlayer.getTotalDuration();
    }

    /**
     * Définit une action à exécuter lorsque le MediaPlayer est prêt à être utilisé.
     *
     * @param var la fonction à exécuter lorsque le MediaPlayer est prêt.
     */
    @Override
    public void setOnReady(Runnable var) {
        this.mediaPlayer.setOnReady(var);
    }

    /**
     * Définit une action à exécuter lorsque le MediaPlayer a terminé de lire le média.
     *
     * @param var la fonction à exécuter lorsque la lecture est terminée.
     */
    @Override
    public void setOnEndOfMedia(Runnable var) {
        this.mediaPlayer.setOnEndOfMedia(var);
    }

    /**
     * Déplace la lecture à un moment précis dans le média.
     *
     * @param time la durée à laquelle se rendre dans le média.
     */
    @Override
    public void seek(Duration time) {
        this.mediaPlayer.seek(time);
    }
}
