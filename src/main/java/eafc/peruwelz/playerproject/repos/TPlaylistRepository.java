package eafc.peruwelz.playerproject.repos;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository pour l'entité {@link TPlaylist}.
 * Cette interface permet d'effectuer des opérations CRUD sur les objets TPlaylist.
 * Elle étend {@link JpaRepository}, offrant ainsi des méthodes prêtes à l'emploi pour interagir avec la base de données.
 */
public interface TPlaylistRepository extends JpaRepository<TPlaylist, Long> {

    /**
     * Recherche une playlist par son nom exact.
     *
     * @param playlist Le nom de la playlist à rechercher.
     * @return L'objet {@link TPlaylist} correspondant au nom exact de la playlist.
     */
    TPlaylist findByPlaylistName(String playlist);

    /**
     * Recherche une liste de playlists en fonction de leur nom, en ignorant la casse.
     *
     * @param playlistName Le nom de la playlist à rechercher.
     * @return Une liste d'objets {@link TPlaylist} correspondant au nom de la playlist, sans tenir compte de la casse.
     */
    List<TPlaylist> findByPlaylistNameIgnoreCase(String playlistName);
}