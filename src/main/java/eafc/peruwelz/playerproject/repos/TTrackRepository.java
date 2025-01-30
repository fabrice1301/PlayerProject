package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour l'entité {@link TTrack}.
 * Cette interface permet d'effectuer des opérations CRUD sur les objets TTrack.
 * Elle étend {@link JpaRepository}, offrant ainsi des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TTrackRepository extends JpaRepository<TTrack, Long> {

    /**
     * Recherche une liste de pistes (tracks) en fonction de leur état d'attente.
     *
     * @param waiting L'état d'attente du morceau (true si en attente, false sinon).
     * @return Une liste d'objets {@link TTrack} dont l'état d'attente correspond au paramètre.
     */
    List<TTrack> findByTrackWaiting(Boolean waiting);

    /**
     * Recherche une liste de pistes qui contiennent un artiste spécifique dans leur liste d'artistes.
     *
     * @param artist L'artiste à rechercher dans la liste des artistes du morceau.
     * @return Une liste d'objets {@link TTrack} contenant l'artiste dans leur liste d'artistes.
     */
    List<TTrack> findByTrackArtistListContains(TArtist artist);

    /**
     * Recherche une liste de pistes qui contiennent un genre spécifique dans leur liste de genres.
     *
     * @param genre Le genre à rechercher dans la liste des genres du morceau.
     * @return Une liste d'objets {@link TTrack} contenant le genre dans leur liste de genres.
     */
    List<TTrack> findByTrackGenreListContains(TGenre genre);

    /**
     * Recherche une liste de pistes qui contiennent un album spécifique dans leur liste d'albums.
     *
     * @param album L'album à rechercher dans la liste des albums du morceau.
     * @return Une liste d'objets {@link TTrack} contenant l'album dans leur liste d'albums.
     */
    List<TTrack> findByTrackAlbumListContains(TAlbum album);

    /**
     * Recherche une liste de pistes qui contiennent une playlist spécifique dans leur liste de playlists.
     *
     * @param playlist La playlist à rechercher dans la liste des playlists du morceau.
     * @return Une liste d'objets {@link TTrack} contenant la playlist dans leur liste de playlists.
     */
    List<TTrack> findByTrackPlaylistListContains(TPlaylist playlist);
}
