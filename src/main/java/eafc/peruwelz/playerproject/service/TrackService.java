package eafc.peruwelz.playerproject.service;

import eafc.peruwelz.playerproject.domain.*;
import eafc.peruwelz.playerproject.repos.TTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service pour la gestion des pistes musicales (tracks).
 */
@Service
public class TrackService {
    /**
     * Repository des tracks, utilisé pour interagir avec la base de données.
     * Il permet de récupérer, sauvegarder, et supprimer des objets {@link TTrack}.
     */
    private final TTrackRepository trackRepository;

    /**
     * Constructeur du service TrackService.
     * @param trackRepository Repository des pistes injecté par Spring.
     */
    @Autowired
    public TrackService(TTrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Supprime une piste du repository.
     * @param track La piste à supprimer.
     */
    public void deleteTrackService(TTrack track){
        this.trackRepository.delete(track);
    }

    /**
     * Sauvegarde une piste dans le repository.
     * @param track La piste à sauvegarder.
     */
    @Transactional
    public void saveTrackService(TTrack track){
        this.trackRepository.save(track);
    }

    /**
     * Récupère toutes les pistes.
     * @return Liste de toutes les pistes.
     */
    public List<TTrack> findAllTrackService(){
        return trackRepository.findAll();
    }

    /**
     * Récupère les pistes en attente.
     * @param waiting Indique si la piste est en attente.
     * @return Liste des pistes en attente.
     */
    public List<TTrack> findByWaitingService(Boolean waiting){
        return trackRepository.findByTrackWaiting(waiting);
    }

    /**
     * Récupère les pistes associées à un artiste donné.
     * @param artist L'artiste dont on veut récupérer les pistes.
     * @return Liste des pistes de l'artiste.
     */
    public List<TTrack> findByArtistService(TArtist artist){
        return trackRepository.findByTrackArtistListContains(artist);
    }

    /**
     * Récupère les pistes associées à un genre donné.
     * @param genre Le genre dont on veut récupérer les pistes.
     * @return Liste des pistes du genre.
     */
    public List<TTrack> findByGenreService(TGenre genre){
        return trackRepository.findByTrackGenreListContains(genre);
    }

    /**
     * Récupère les pistes associées à un album donné.
     * @param album L'album dont on veut récupérer les pistes.
     * @return Liste des pistes de l'album.
     */
    public List<TTrack> findByAlbumService(TAlbum album){
        return trackRepository.findByTrackAlbumListContains(album);
    }

    /**
     * Récupère les pistes associées à une playlist donnée.
     * @param playlist La playlist dont on veut récupérer les pistes.
     * @return Liste des pistes de la playlist.
     */
    public List<TTrack> findByPlaylistService(TPlaylist playlist){
        return trackRepository.findByTrackPlaylistListContains(playlist);
    }
}

