package eafc.peruwelz.PlayerProject.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Duration;
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

    @Column
    private Integer trackCount;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean trackDeleted=false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Tplaylisttrack",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "playlistId")
    )
    private Set<TPlaylist> trackPlaylistList;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackartist",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "artistId")
    )
    private Set<TArtist> trackArtistList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackalbum",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "albumId")
    )
    private Set<TAlbum> trackAlbumList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackgenre",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<TGenre> trackGenreList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_listen_list_id")
    private TListen trackListenList;

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

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(final Integer trackCount) {
        this.trackCount = trackCount;
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

    public TListen getTrackListenList() {
        return trackListenList;
    }

    public void setTrackListenList(final TListen trackListenList) {
        this.trackListenList = trackListenList;
    }

    public String getTrackGenresAsString() {
        if (trackGenreList == null || trackGenreList.isEmpty()) {
            return "";
        }
        return trackGenreList.stream() //On transforme trackGenreList en un flux de données
                .map(genre -> genre.getGenreName()) // On récupère le nom de chaque TGenre
                .collect(Collectors.joining(", ")); //On concatène les noms avec une virgule et un espace entre eux
    }

    public String getTrackArtistsAsString() {
        if (trackArtistList == null || trackArtistList.isEmpty()) {
            return "";
        }
        return trackArtistList.stream() //On transforme trackGenreList en un flux de données
                .map(artist -> artist.getArtistName()) // On récupère le nom de chaque TGenre
                .collect(Collectors.joining(", ")); //On concatène les noms avec une virgule et un espace entre eux
    }

    public String getTrackAlbumsAsString() {
        if (trackAlbumList == null || trackAlbumList.isEmpty()) {
            return "";
        }
        return trackAlbumList.stream() //On transforme trackGenreList en un flux de données
                .map(album -> album.getAlbumName()) // On récupère le nom de chaque TGenre
                .collect(Collectors.joining(", ")); //On concatène les noms avec une virgule et un espace entre eux
    }

    public String getTrackPlaylistsAsString() {
        if (trackPlaylistList == null || trackPlaylistList.isEmpty()) {
            return "";
        }
        return trackPlaylistList.stream() //On transforme trackGenreList en un flux de données
                .map(playlist -> playlist.getPlaylistName()) // On récupère le nom de chaque TGenre
                .collect(Collectors.joining(", ")); //On concatène les noms avec une virgule et un espace entre eux
    }

    public String getTrackTimeFormat() {
        int minutes = trackTime / 60;
        int seconds = trackTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
