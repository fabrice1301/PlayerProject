package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class TPlaylist {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;

    @Column
    private String playlistName;

    @Column
    private Integer playlistCount;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean playlistDeleted;

    @ManyToMany
    @JoinTable(
            name = "Tplaylisttrack",
            joinColumns = @JoinColumn(name = "playlistId"),
            inverseJoinColumns = @JoinColumn(name = "trackId")
    )
    private Set<TTrack> playlistTrackList;


    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(final Long playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    public Integer getPlaylistCount() {
        return playlistCount;
    }

    public void setPlaylistCount(final Integer playlistCount) {
        this.playlistCount = playlistCount;
    }

    public Boolean getPlaylistDeleted() {
        return playlistDeleted;
    }

    public void setPlaylistDeleted(final Boolean playlistDeleted) {
        this.playlistDeleted = playlistDeleted;
    }

    public Set<TTrack> getPlaylistTrackList() {
        return playlistTrackList;
    }

    public void setPlaylistTrackList(final Set<TTrack> playlistTrackList) {
        this.playlistTrackList = playlistTrackList;
    }


}
