package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TAlbum;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Modèle représentant un album avec une propriété de sélection.
 * Cette classe étend l'entité {@link TAlbum} et permet de gérer l'état de sélection de l'album.
 */
public class AlbumModelSelection extends TAlbum {

    /** L'album associé à cet objet modèle. */
    private final TAlbum album;

    /** Propriété qui indique si l'album est sélectionné ou non. */
    private final BooleanProperty selectedAlbum;

    /**
     * Constructeur du modèle AlbumModelSelection.
     *
     * @param album L'album à associer à cet objet modèle.
     * @param selected L'état de sélection initial de l'album (true si sélectionné, false sinon).
     */
    public AlbumModelSelection(TAlbum album, boolean selected) {
        this.album = album;
        this.selectedAlbum = new SimpleBooleanProperty(selected);
    }

    /**
     * Récupère l'album associé à cet objet modèle.
     *
     * @return L'album associé.
     */
    public TAlbum getAlbum() {
        return album;
    }

    /**
     * Vérifie si l'album est sélectionné.
     *
     * @return true si l'album est sélectionné, false sinon.
     */
    public boolean isSelected() {
        return selectedAlbum.get();
    }

    /**
     * Récupère la propriété de sélection de l'album.
     * Cette propriété permet de lier l'état de sélection à des éléments graphiques (par exemple, une case à cocher).
     *
     * @return La propriété {@link BooleanProperty} représentant l'état de sélection de l'album.
     */
    public BooleanProperty selectedProperty() {
        return selectedAlbum;
    }

    /**
     * Modifie l'état de sélection de l'album.
     *
     * @param selected L'état de sélection à définir (true pour sélectionné, false pour non sélectionné).
     */
    public void setSelected(boolean selected) {
        this.selectedAlbum.set(selected);
    }
}

