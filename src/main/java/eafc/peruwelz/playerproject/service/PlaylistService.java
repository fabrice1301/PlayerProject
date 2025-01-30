package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.TPlaylist;
import eafc.peruwelz.playerproject.domain.TTrack;
import eafc.peruwelz.playerproject.repos.TPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des playlists.
 */
@Service
public class PlaylistService {

    /**
     * Repository des playlists, utilisé pour interagir avec la base de données.
     * Il permet de récupérer, sauvegarder, et supprimer des objets {@link TPlaylist}.
     */
    private final TPlaylistRepository playlistRepository;

    /**
     * Constructeur du service PlaylistService.
     * @param playlistRepository Repository des playlists injecté par Spring.
     */
    @Autowired
    public PlaylistService(TPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    /**
     * Récupère toutes les playlists triées par nom en ordre alphabétique.
     * @return Liste triée de toutes les playlists.
     */
    public List<TPlaylist> findAllPlaylistService(){
        return playlistRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(playlist -> playlist.getPlaylistName().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Récupère une playlist par son nom.
     * @param playlist Le nom de la playlist à rechercher.
     * @return La playlist correspondante.
     */
    public TPlaylist findByPlaylistIdService(String playlist){
        return playlistRepository.findByPlaylistName(playlist);
    }

    /**
     * Sauvegarde une playlist dans le repository.
     * @param playlist La playlist à sauvegarder.
     */
    public void savePlaylistService(TPlaylist playlist){
        playlistRepository.save(playlist);
    }

    /**
     * Supprime une playlist du repository.
     * @param playlist La playlist à supprimer.
     */
    public void deletePlaylistService(TPlaylist playlist){
        playlistRepository.delete(playlist);
    }

    /**
     * Récupère les pistes d'une playlist.
     * @param playlist La playlist dont on veut obtenir les pistes.
     * @return Ensemble des pistes de la playlist.
     */
    public Set<TTrack> getTrack(TPlaylist playlist){
        return playlist.getPlaylistTrackList();
    }

    /**
     * Vérifie si une playlist existe déjà par son nom.
     * @param name Le nom de la playlist à vérifier.
     * @return true si la playlist existe, false sinon.
     */
    public Boolean isExists(String name){
        return !playlistRepository.findByPlaylistNameIgnoreCase(name).isEmpty();
    }
}
