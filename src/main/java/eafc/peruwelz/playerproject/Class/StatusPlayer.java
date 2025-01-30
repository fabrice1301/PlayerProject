package eafc.peruwelz.playerproject.Class;

/**
 * Classe qui gère le statut du lecteur de musique (Lecture, Pause, Arrêt, Inconnu).
 * Utilise le patron de conception Singleton pour s'assurer qu'il n'y a qu'une seule instance de cette classe.
 */
public class StatusPlayer {

    /**
     * Enumération représentant les différents états du lecteur.
     */
    private enum STATUS {
        PLAYING,  // Le lecteur est en train de jouer
        STOPPED,  // Le lecteur est arrêté
        PAUSED,   // Le lecteur est en pause
        UNKNOW    // L'état du lecteur est inconnu
    }

    private STATUS status;  // Le statut actuel du lecteur
    private static StatusPlayer instance;  // L'instance unique de StatusPlayer

    /**
     * Constructeur privé pour empêcher la création d'instances en dehors de la classe.
     * Initialise le statut à INCONNU.
     */
    private StatusPlayer() {
        status = STATUS.UNKNOW;
    }

    /**
     * Méthode pour obtenir l'instance unique de StatusPlayer (patron Singleton).
     * Si l'instance n'existe pas, elle est créée.
     *
     * @return L'instance unique de StatusPlayer.
     */
    public static StatusPlayer getIntance() {
        if (instance == null) {
            instance = new StatusPlayer();
        }
        return instance;
    }

    /**
     * Méthode pour obtenir le statut actuel du lecteur sous forme de chaîne de caractères.
     *
     * @return Le statut actuel du lecteur en format String.
     */
    public String getStatus() {
        return status.toString();
    }

    /**
     * Méthode pour définir le statut du lecteur.
     * Le statut est converti en majuscules avant d'être attribué à l'enum STATUS.
     *
     * @param status Le statut à définir sous forme de chaîne de caractères (ex : "PLAYING", "PAUSED").
     */
    public void setStatus(String status) {
        this.status = STATUS.valueOf(status.toUpperCase());
    }
}
