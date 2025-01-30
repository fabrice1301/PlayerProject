package eafc.peruwelz.playerproject.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TTrack {

    /** Identifiant unique de la piste. */
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    /** Titre de la piste. */
    @Column
    private String trackTitle;

    /** Chemin du fichier audio associé à la piste. */
    @Column
    private String trackPath;

    /** Chemin de la pochette associée à la piste */
    @Column
    private String trackPicture;

    /** Durée de la piste en secondes. */
    @Column
    private Integer trackTime;

    /** Date de sortie de la piste. */
    @Column
    private LocalDate trackDate;

    /** Indique si la piste se trouve dans la liste d'écoute */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean trackWaiting;

    /** Liste des playlists contenant cette piste. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Tplaylisttrack",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "playlistId")
    )
    private Set<TPlaylist> trackPlaylistList;

    /** Liste des artistes associés à cette piste. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackartist",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "artistId")
    )
    private Set<TArtist> trackArtistList;

    /** Liste des albums associés à cette piste. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackalbum",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "albumId")
    )
    private Set<TAlbum> trackAlbumList;

    /** Liste des genres associés à cette piste. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Ttrackgenre",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<TGenre> trackGenreList;

    /** Constructeur par défaut. Initialise la piste comme "pas dans la liste d'écoute". */
    @Autowired
    public TTrack(){
        this.trackWaiting = false;
    }

    /**
     * Retourne l'identifiant unique de la piste.
     * @return trackId id de la piste
     */
    public Long getTrackId() {
        return trackId;
    }

    /**
     * Retourne le titre de la piste.
     * @return trackTitle titre de la piste
     */
    public String getTrackTitle() {
        return trackTitle;
    }

    /**
     * Définit le titre de la piste.
     * @param  trackTitle piste pour laquelle le titre sera modifié
     */
    public void setTrackTitle(final String trackTitle) {
        this.trackTitle = trackTitle;
    }

    /**
     * Retourne le chemin du fichier de la piste.
     */
    public String getTrackPath() {
        return trackPath;
    }

    /**
     * Définit le chemin du fichier de la piste.
     */
    public void setTrackPath(final String trackPath) {
        this.trackPath = trackPath;
    }

    /**
     * Retourne le chemin de la pochette de la piste.
     */
    public String getTrackPicture() {
        return trackPicture;
    }

    /**
     * Définit le chemin de la pochette de la piste.
     */
    public void setTrackPicture(final String trackPicture) {
        this.trackPicture = trackPicture;
    }

    /**
     * Retourne la durée de la piste en secondes.
     */
    public Integer getTrackTime() {
        return trackTime;
    }

    /**
     * Définit la durée de la piste en secondes.
     */
    public void setTrackTime(final Integer trackTime) {
        this.trackTime = trackTime;
    }

    /**
     * Retourne la date de sortie de la piste ou de l'album
     */
    public LocalDate getTrackDate() {
        return trackDate;
    }

    /**
     * Définit la date de sortie de la piste ou de l'album
     */
    public void setTrackDate(final LocalDate trackDate) {
        this.trackDate = trackDate;
    }

    /**
     * Retourne si la piste est dans la liste d'écoute
     */
    public Boolean getTrackWaiting() {
        return trackWaiting;
    }

    /**
     * Définit si la piste est dans la liste d'écoute
     */
    public void setTrackWaiting(final Boolean trackWaiting) {
        this.trackWaiting = trackWaiting;
    }

    /**
     * Retourne la liste des playlists contenant cette piste.
     */
    public Set<TPlaylist> getTrackPlaylistList() {
        return trackPlaylistList;
    }

    /**
     * Définit la liste des playlists contenant cette piste.
     */
    public void setTrackPlaylistList(final Set<TPlaylist> trackPlaylistList) {
        this.trackPlaylistList = trackPlaylistList;
    }

    /**
     * Retourne la liste des artistes associés à cette piste.
     */
    public Set<TArtist> getTrackArtistList() {
        return trackArtistList;
    }

    /**
     * Définit la liste des artistes associés à cette piste.
     */
    public void setTrackArtistList(final Set<TArtist> trackArtistList) {
        this.trackArtistList = trackArtistList;
    }

    /**
     * Retourne la liste des albums associés à cette piste.
     */
    public Set<TAlbum> getTrackAlbumList() {
        return trackAlbumList;
    }

    /**
     * Définit la liste des albums associés à cette piste.
     */
    public void setTrackAlbumList(final Set<TAlbum> trackAlbumList) {
        this.trackAlbumList = trackAlbumList;
    }

    /**
     * Retourne la liste des genres associés à cette piste.
     */
    public Set<TGenre> getTrackGenreList() {
        return trackGenreList;
    }

    /**
     * Définit la liste des genres associés à cette piste.
     */
    public void setTrackGenreList(final Set<TGenre> trackGenreList) {
        this.trackGenreList = trackGenreList;
    }

    /**
     * Retourne les noms des genres associés à la piste sous forme de chaîne, séparés par des virgules.
     */
    public String getTrackGenresAsString() {
        if (trackGenreList == null || trackGenreList.isEmpty()) {
            return "";
        }
        return trackGenreList.stream()
                .map(genre -> genre.getGenreName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Retourne les noms des artistes associés à la piste sous forme de chaîne, séparés par des virgules.
     * @return Une chaîne contenant les noms des artistes, ou une chaîne vide si aucun artiste n'est associé.
     */
    public String getTrackArtistsAsString() {
        if (trackArtistList == null || trackArtistList.isEmpty()) {
            return "";
        }
        return trackArtistList.stream()
                .map(artist -> artist.getArtistName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Retourne les noms des albums associés à la piste sous forme de chaîne, séparés par des virgules.
     * @return Une chaîne contenant les noms des albums, ou une chaîne vide si aucun album n'est associé.
     */
    public String getTrackAlbumsAsString() {
        if (trackAlbumList == null || trackAlbumList.isEmpty()) {
            return "";
        }
        return trackAlbumList.stream()
                .map(album -> album.getAlbumName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Retourne les noms des playlists contenant cette piste sous forme de chaîne, séparés par des virgules.
     * @return Une chaîne contenant les noms des playlists, ou une chaîne vide si aucune playlist n'est associée.
     */
    public String getTrackPlaylistsAsString() {
        if (trackPlaylistList == null || trackPlaylistList.isEmpty()) {
            return "";
        }
        return trackPlaylistList.stream()
                .map(playlist -> playlist.getPlaylistName())
                .collect(Collectors.joining(", "));
    }

    /**
     * Formate la durée de la piste (en secondes) en une chaîne au format "mm:ss".
     * @return Une chaîne représentant la durée formatée (par exemple, "03:45").
     */
    public String getTrackTimeFormat() {
        int minutes = trackTime / 60;
        int seconds = trackTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Compare cet objet avec un autre pour vérifier leur égalité.
     * @param o L'objet à comparer.
     * @return true si les deux objets sont identiques ou partagent des champs équivalents, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TTrack tTrack = (TTrack) o;

        if (trackId != null && tTrack.trackId != null) {
            return trackId.equals(tTrack.trackId);
        }

        return (trackTitle != null ? trackTitle.equals(tTrack.trackTitle) : tTrack.trackTitle == null) &&
                (trackPath != null ? trackPath.equals(tTrack.trackPath) : tTrack.trackPath == null) &&
                (trackDate != null ? trackDate.equals(tTrack.trackDate) : tTrack.trackDate == null);
    }

    /**
     * Crée une copie de cette instance de TTrack, y compris ses listes associées.
     * @return Une nouvelle instance de TTrack avec les mêmes valeurs que l'instance actuelle.
     */
    public TTrack copy() {
        TTrack copy = new TTrack();
        copy.setTrackTitle(this.trackTitle);
        copy.setTrackPath(this.trackPath);
        copy.setTrackPicture(this.trackPicture);
        copy.setTrackTime(this.trackTime);
        copy.setTrackDate(this.trackDate);
        copy.setTrackWaiting(this.trackWaiting);
        if (this.trackArtistList != null) {
            copy.setTrackArtistList(this.trackArtistList.stream().collect(Collectors.toSet()));
        }
        if (this.trackAlbumList != null) {
            copy.setTrackAlbumList(this.trackAlbumList.stream().collect(Collectors.toSet()));
        }
        if (this.trackGenreList != null) {
            copy.setTrackGenreList(this.trackGenreList.stream().collect(Collectors.toSet()));
        }
        if (this.trackPlaylistList != null) {
            copy.setTrackPlaylistList(this.trackPlaylistList.stream().collect(Collectors.toSet()));
        }
        return copy;
    }
}
