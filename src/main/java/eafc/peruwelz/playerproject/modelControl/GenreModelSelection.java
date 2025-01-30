package eafc.peruwelz.playerproject.modelControl;

import eafc.peruwelz.playerproject.domain.TGenre;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Modèle représentant un genre musical avec une propriété de sélection.
 * Cette classe étend l'entité {@link TGenre} et permet de gérer l'état de sélection du genre.
 */
public class GenreModelSelection extends TGenre {

    /** Le genre musical associé à cet objet modèle. */
    private final TGenre genre;

    /** Propriété qui indique si le genre est sélectionné ou non. */
    private final BooleanProperty selectedGenre;

    /**
     * Constructeur du modèle GenreModelSelection.
     *
     * @param genre Le genre à associer à cet objet modèle.
     * @param selected L'état de sélection initial du genre (true si sélectionné, false sinon).
     */
    public GenreModelSelection(TGenre genre, boolean selected) {
        this.genre = genre;
        this.selectedGenre = new SimpleBooleanProperty(selected);
    }

    /**
     * Récupère le genre associé à cet objet modèle.
     *
     * @return Le genre associé.
     */
    public TGenre getGenre() {
        return genre;
    }

    /**
     * Vérifie si le genre est sélectionné.
     *
     * @return true si le genre est sélectionné, false sinon.
     */
    public boolean isSelected() {
        return selectedGenre.get();
    }

    /**
     * Récupère la propriété de sélection du genre.
     * Cette propriété permet de lier l'état de sélection à des éléments graphiques (par exemple, une case à cocher).
     *
     * @return La propriété {@link BooleanProperty} représentant l'état de sélection du genre.
     */
    public BooleanProperty selectedProperty() {
        return selectedGenre;
    }

    /**
     * Modifie l'état de sélection du genre.
     *
     * @param selected L'état de sélection à définir (true pour sélectionné, false pour non sélectionné).
     */
    public void setSelected(boolean selected) {
        this.selectedGenre.set(selected);
    }
}

