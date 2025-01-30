package eafc.peruwelz.playerproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Entité représentant une playlist dans l'application.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TPlaylist {

    /**
     * Identifiant unique de la playlist.
     */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;

    /**
     * Nom de la playlist.
     */
    @Column
    private String playlistName;

    /**
     * Liste des pistes associées à la playlist.
     */
    @ManyToMany
    @JoinTable(
            name = "Tplaylisttrack",
            joinColumns = @JoinColumn(name = "playlistId"),
            inverseJoinColumns = @JoinColumn(name = "trackId")
    )
    private Set<TTrack> playlistTrackList;

    /**
     * Retourne l'identifiant unique de la playlist.
     * @return l'identifiant de la playlist.
     */
    public Long getPlaylistId() {
        return playlistId;
    }

    /**
     * Définit l'identifiant unique de la playlist.
     * @param playlistId nouvel identifiant de la playlist.
     */
    public void setPlaylistId(final Long playlistId) {
        this.playlistId = playlistId;
    }

    /**
     * Retourne le nom de la playlist.
     * @return le nom de la playlist.
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * Définit le nom de la playlist.
     * @param playlistName nouveau nom de la playlist.
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     * Retourne la liste des pistes associées à la playlist.
     * Cette méthode est marquée comme transactionnelle en lecture seule.
     * @return un ensemble de pistes associées à la playlist.
     */
    @Transactional(readOnly = true)
    public Set<TTrack> getPlaylistTrackList() {
        return playlistTrackList;
    }

    /**
     * Définit la liste des pistes associées à la playlist.
     * @param playlistTrackList nouvelle liste des pistes associées.
     */
    public void setPlaylistTrackList(final Set<TTrack> playlistTrackList) {
        this.playlistTrackList = playlistTrackList;
    }

    /**
     * Vérifie l'égalité entre deux objets TPlaylist en se basant sur leur identifiant unique.
     * @param o l'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TPlaylist playlist)) return false;
        return Objects.equals(getPlaylistId(), playlist.getPlaylistId());
    }

    /**
     * Retourne le hashcode de la playlist basé sur son identifiant unique.
     * @return hashcode de la playlist.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPlaylistId());
    }
}
