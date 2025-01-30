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
 * Représente un album musical.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TAlbum {

    /**
     * Identifiant unique de l'album.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    /**
     * Nom de l'album.
     */
    @Column
    private String albumName;

    /**
     * Liste des pistes associées à cet album.
     */
    @ManyToMany(mappedBy = "trackAlbumList")
    private Set<TTrack> albumTrackList;

    /**
     * Obtient l'identifiant de l'album.
     *
     * @return l'identifiant de l'album
     */
    public Long getAlbumId() {
        return albumId;
    }

    /**
     * Définit l'identifiant de l'album.
     *
     * @param albumId l'identifiant de l'album
     */
    public void setAlbumId(final Long albumId) {
        this.albumId = albumId;
    }

    /**
     * Obtient le nom de l'album.
     *
     * @return le nom de l'album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Définit le nom de l'album.
     *
     * @param albumName le nom de l'album
     */
    public void setAlbumName(final String albumName) {
        this.albumName = albumName;
    }

    /**
     * Obtient la liste des pistes de l'album.
     *
     * @return la liste des pistes de l'album
     */
    public Set<TTrack> getAlbumTrackList() {
        return albumTrackList;
    }

    /**
     * Définit la liste des pistes de l'album.
     *
     * @param albumTrackList la liste des pistes de l'album
     */
    public void setAlbumTrackList(final Set<TTrack> albumTrackList) {
        this.albumTrackList = albumTrackList;
    }

    /**
     * Vérifie l'égalité de l'album en se basant sur l'identifiant.
     *
     * @param o l'objet à comparer
     * @return true si les albums sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TAlbum album)) return false;
        return Objects.equals(getAlbumId(), album.getAlbumId());
    }

    /**
     * Génère un code de hachage basé sur l'identifiant de l'album.
     *
     * @return le code de hachage de l'album
     */
    @Override
    public int hashCode() {
        return Objects.hash(getAlbumId());
    }
}
