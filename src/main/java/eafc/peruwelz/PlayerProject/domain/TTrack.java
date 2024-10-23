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
import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class TTrack {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    @Column
    private String trackTitle;

    @Column
    private String trackPath;

    @Column
    private String trackPicture;

    @Column
    private Integer trackTime;

    @Column
    private LocalDate trackDate;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean trackDeleted;

    @ManyToMany(mappedBy = "playlistTrackList")
    private Set<TPlaylist> trackPlaylistList;

    @ManyToMany
    @JoinTable(
            name = "Ttrackartist",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<TArtist> trackArtistList;

    @ManyToMany
    @JoinTable(
            name = "Ttrackalbum",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "albumId")
    )
    private Set<TAlbum> trackAlbumList;

    @ManyToMany
    @JoinTable(
            name = "Ttrackgenre",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<TGenre> trackGenreList;




    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(final Long trackId) {
        this.trackId = trackId;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(final String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getTrackPath() {
        return trackPath;
    }

    public void setTrackPath(final String trackPath) {
        this.trackPath = trackPath;
    }

    public String getTrackPicture() {
        return trackPicture;
    }

    public void setTrackPicture(final String trackPicture) {
        this.trackPicture = trackPicture;
    }

    public Integer getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(final Integer trackTime) {
        this.trackTime = trackTime;
    }

    public LocalDate getTrackDate() {
        return trackDate;
    }

    public void setTrackDate(final LocalDate trackDate) {
        this.trackDate = trackDate;
    }

    public Boolean getTrackDeleted() {
        return trackDeleted;
    }

    public void setTrackDeleted(final Boolean trackDeleted) {
        this.trackDeleted = trackDeleted;
    }

    public Set<TPlaylist> getTrackPlaylistList() {
        return trackPlaylistList;
    }

    public void setTrackPlaylistList(final Set<TPlaylist> trackPlaylistList) {
        this.trackPlaylistList = trackPlaylistList;
    }

    public Set<TArtist> getTrackArtistList() {
        return trackArtistList;
    }

    public void setTrackArtistList(final Set<TArtist> trackArtistList) {
        this.trackArtistList = trackArtistList;
    }

    public Set<TAlbum> getTrackAlbumList() {
        return trackAlbumList;
    }

    public void setTrackAlbumList(final Set<TAlbum> trackAlbumList) {
        this.trackAlbumList = trackAlbumList;
    }

    public Set<TGenre> getTrackGenreList() {
        return trackGenreList;
    }

    public void setTrackGenreList(final Set<TGenre> trackGenreList) {
        this.trackGenreList = trackGenreList;
    }



}
