package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TArtist;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Modèle représentant un artiste avec une propriété de sélection.
 * Cette classe étend l'entité {@link TArtist} et permet de gérer l'état de sélection de l'artiste.
 */
public class ArtistModelSelection extends TArtist {

    /** L'artiste associé à cet objet modèle. */
    private final TArtist artist;

    /** Propriété qui indique si l'artiste est sélectionné ou non. */
    private final BooleanProperty selectedArtist;

    /**
     * Constructeur du modèle ArtistModelSelection.
     *
     * @param artist L'artiste à associer à cet objet modèle.
     * @param selected L'état de sélection initial de l'artiste (true si sélectionné, false sinon).
     */
    public ArtistModelSelection(TArtist artist, boolean selected) {
        this.artist = artist;
        this.selectedArtist = new SimpleBooleanProperty(selected);
    }

    /**
     * Récupère l'artiste associé à cet objet modèle.
     *
     * @return L'artiste associé.
     */
    public TArtist getArtist() {
        return artist;
    }

    /**
     * Vérifie si l'artiste est sélectionné.
     *
     * @return true si l'artiste est sélectionné, false sinon.
     */
    public boolean isSelected() {
        return selectedArtist.get();
    }

    /**
     * Récupère la propriété de sélection de l'artiste.
     * Cette propriété permet de lier l'état de sélection à des éléments graphiques (par exemple, une case à cocher).
     *
     * @return La propriété {@link BooleanProperty} représentant l'état de sélection de l'artiste.
     */
    public BooleanProperty selectedProperty() {
        return selectedArtist;
    }

    /**
     * Modifie l'état de sélection de l'artiste.
     *
     * @param selected L'état de sélection à définir (true pour sélectionné, false pour non sélectionné).
     */
    public void setSelected(boolean selected) {
        this.selectedArtist.set(selected);
    }
}
