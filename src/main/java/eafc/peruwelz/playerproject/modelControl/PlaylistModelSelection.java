package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Modèle représentant une playlist avec une propriété de sélection.
 * Cette classe étend l'entité {@link TPlaylist} et permet de gérer l'état de sélection de la playlist.
 */
public class PlaylistModelSelection extends TPlaylist {

    /** La playlist associée à cet objet modèle. */
    private final TPlaylist playlist;

    /** Propriété qui indique si la playlist est sélectionnée ou non. */
    private final BooleanProperty selectedPlaylist;

    /**
     * Constructeur du modèle PlaylistModelSelection.
     *
     * @param playlist La playlist à associer à cet objet modèle.
     * @param selected L'état de sélection initial de la playlist (true si sélectionnée, false sinon).
     */
    public PlaylistModelSelection(TPlaylist playlist, boolean selected) {
        this.playlist = playlist;
        this.selectedPlaylist = new SimpleBooleanProperty(selected);
    }

    /**
     * Récupère la playlist associée à cet objet modèle.
     *
     * @return La playlist associée.
     */
    public TPlaylist getPlaylist() {
        return playlist;
    }

    /**
     * Vérifie si la playlist est sélectionnée.
     *
     * @return true si la playlist est sélectionnée, false sinon.
     */
    public boolean isSelected() {
        return selectedPlaylist.get();
    }

    /**
     * Récupère la propriété de sélection de la playlist.
     * Cette propriété permet de lier l'état de sélection à des éléments graphiques (par exemple, une case à cocher).
     *
     * @return La propriété {@link BooleanProperty} représentant l'état de sélection de la playlist.
     */
    public BooleanProperty selectedProperty() {
        return selectedPlaylist;
    }

    /**
     * Modifie l'état de sélection de la playlist.
     *
     * @param selected L'état de sélection à définir (true pour sélectionnée, false pour non sélectionnée).
     */
    public void setSelected(boolean selected) {
        this.selectedPlaylist.set(selected);
    }
}

