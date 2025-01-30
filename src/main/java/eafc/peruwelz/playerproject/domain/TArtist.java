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
 * Entity représentant un artiste.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TArtist {

    /**
     * Identifiant unique de l'artiste.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    /**
     * Nom de l'artiste.
     */
    @Column
    private String artistName;

    /**
     * Liste des pistes associées à l'artiste.
     */
    @ManyToMany(mappedBy = "trackArtistList")
    private Set<TTrack> artistTrackList;

    /**
     * Récupère l'identifiant de l'artiste.
     *
     * @return L'identifiant de l'artiste.
     */
    public Long getArtistId() {
        return artistId;
    }

    /**
     * Définit l'identifiant de l'artiste.
     *
     * @param artistId L'identifiant de l'artiste.
     */
    public void setArtistId(final Long artistId) {
        this.artistId = artistId;
    }

    /**
     * Récupère le nom de l'artiste.
     *
     * @return Le nom de l'artiste.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Définit le nom de l'artiste.
     *
     * @param artistName Le nom de l'artiste.
     */
    public void setArtistName(final String artistName) {
        this.artistName = artistName;
    }

    /**
     * Récupère la liste des pistes associées à l'artiste.
     *
     * @return La liste des pistes associées à l'artiste.
     */
    public Set<TTrack> getArtistTrackList() {
        return artistTrackList;
    }

    /**
     * Définit la liste des pistes associées à l'artiste.
     *
     * @param artistTrackList La liste des pistes associées à l'artiste.
     */
    public void setArtistTrackList(final Set<TTrack> artistTrackList) {
        this.artistTrackList = artistTrackList;
    }

    /**
     * Vérifie l'égalité entre deux objets TArtist.
     *
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TArtist artist)) return false;
        return Objects.equals(getArtistId(), artist.getArtistId());
    }

    /**
     * Calcule le hashCode de l'entité TArtist.
     *
     * @return Le hashCode basé sur l'identifiant de l'artiste.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getArtistId());
    }
}

