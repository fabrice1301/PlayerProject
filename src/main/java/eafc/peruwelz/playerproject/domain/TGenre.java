package eafc.peruwelz.playerproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entité représentant un genre musical dans l'application.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TGenre {

    /**
     * Identifiant unique du genre musical.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;

    /**
     * Nom du genre musical.
     */
    @Column
    private String genreName;

    /**
     * Liste des pistes associées à ce genre musical.
     */
    @ManyToMany(mappedBy = "trackGenreList")
    private Set<TTrack> genreTrackList;

    /**
     * Retourne l'identifiant unique du genre musical.
     * @return l'identifiant du genre.
     */
    public Long getGenreId() {
        return genreId;
    }

    /**
     * Définit l'identifiant unique du genre musical.
     * @param genreId nouvel identifiant du genre.
     */
    public void setGenreId(final Long genreId) {
        this.genreId = genreId;
    }

    /**
     * Retourne le nom du genre musical.
     * @return le nom du genre.
     */
    public String getGenreName() {
        return genreName;
    }

    /**
     * Définit le nom du genre musical.
     * @param genreName nouveau nom du genre.
     */
    public void setGenreName(final String genreName) {
        this.genreName = genreName;
    }

    /**
     * Retourne la liste des pistes associées à ce genre musical.
     * @return un ensemble de pistes associées.
     */
    public Set<TTrack> getGenreTrackList() {
        return genreTrackList;
    }

    /**
     * Définit la liste des pistes associées à ce genre musical.
     * @param genreTrackList nouvelle liste des pistes associées.
     */
    public void setGenreTrackList(final Set<TTrack> genreTrackList) {
        this.genreTrackList = genreTrackList;
    }

    /**
     * Vérifie l'égalité entre deux objets TGenre en se basant sur leur identifiant unique.
     * @param o l'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TGenre genre)) return false;
        return Objects.equals(getGenreId(), genre.getGenreId());
    }

    /**
     * Retourne le hashcode du genre musical basé sur son identifiant unique.
     * @return hashcode du genre musical.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getGenreId());
    }
}
